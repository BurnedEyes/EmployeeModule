package com.employees.module.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employees.module.dao.EmployeeDao;
import com.employees.module.dao.SalaryGroupDao;
import com.employees.module.dto.SalaryGroupWithCountsDto;
import com.employees.module.entity.Employee;
import com.employees.module.entity.SalaryGroup;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private SalaryGroupDao salaryGroupDao;

	private EmployeeDao employeeDao;

	@Autowired
	public EmployeeServiceImpl(SalaryGroupDao salaryGroupDao, EmployeeDao employeeDao) {
		this.salaryGroupDao = salaryGroupDao;
		this.employeeDao = employeeDao;
	}

	@Override
	public List<SalaryGroupWithCountsDto> calculateEmployeesAccordingToSalaryGroups() {
		Iterable<SalaryGroup> salaryGroups = salaryGroupDao.findAll();
		List<SalaryGroupWithCountsDto> salaryGroupsWithCounts = new ArrayList<>();
		salaryGroups.forEach(sg -> salaryGroupsWithCounts
				.add(SalaryGroupWithCountsDto.createDto(sg, employeeDao.countBySalaryGroup(sg))));
		return salaryGroupsWithCounts;
	}

	@Override
	public void reassignEmployeesToAppropriateSalaryGroups(LocalDateTime lastTime) {
		employeeDao.findAll().forEach(e -> {
			if (shouldEmployeeBeReassigned(e, lastTime)) {
				salaryGroupDao.findByFromValueLessThanAndToValueGreaterThanEqual(e.getSalary(), e.getSalary())
						.ifPresent(sg -> {
							e.setSalaryGroup(sg);
							employeeDao.save(e);
						});
			}
		});
	}

	private boolean shouldEmployeeBeReassigned(Employee employee, LocalDateTime lastRaportGeneratedDateTime) {
		if (employee.getSalaryGroup() == null) {
			return true;
		}
		return lastRaportGeneratedDateTime.isBefore(employee.getLastModifiedDateTime())
				|| lastRaportGeneratedDateTime.isBefore(employee.getSalaryGroup().getLastModifiedDateTime());
	}
}
