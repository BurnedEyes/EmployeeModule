package com.employees.module.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employees.module.dao.EmployeeDao;
import com.employees.module.dto.EmployeeDto;
import com.employees.module.entity.Employee;
import com.employees.module.exception.ResourceNotFoundException;
import com.employees.module.exception.ResourceWithGivenIdAlreadyExists;

@RestController
@RequestMapping(URL.EMPLOYEE)
public class EmployeeController {

	EmployeeDao employeeRepository;

	@Autowired
	public EmployeeController(EmployeeDao employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping
	public List<EmployeeDto> getAllEmployees() {
		return StreamSupport.stream(employeeRepository.findAll().spliterator(), false).map(EmployeeDto::toDto)
				.collect(Collectors.toList());
	}

	@PostMapping(URL.EMPLOYEE_ADD)
	public EmployeeDto createEmployee(@RequestBody Employee employee) {
		employeeRepository.findById(employee.getId()).ifPresent(x -> {
			throw new ResourceWithGivenIdAlreadyExists(Employee.class, employee.getId());
		});
		Employee savedEmployee = employeeRepository.save(employee);
		return EmployeeDto.toDto(savedEmployee);
	}

	@GetMapping(URL.EMPLOYEE_ID)
	public EmployeeDto retrieveEmployee(@PathVariable long id) {
		return employeeRepository.findById(id).map(e -> EmployeeDto.toDto(e))
				.orElseThrow(() -> new ResourceNotFoundException(Employee.class, id));
	}

	@GetMapping(URL.EMPLOYEE_FROM_SALARY)
	public List<EmployeeDto> listEmployeesWithSalaryGreaterThan(@PathVariable BigDecimal salary) {
		List<EmployeeDto> result = new ArrayList<>();
		employeeRepository.findBySalaryGreaterThanEqual(salary).forEach(e -> result.add(EmployeeDto.toDto(e)));
		return result;
	}
}