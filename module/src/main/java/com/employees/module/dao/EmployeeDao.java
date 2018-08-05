package com.employees.module.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.employees.module.entity.Employee;
import com.employees.module.entity.SalaryGroup;

public interface EmployeeDao extends CrudRepository<Employee, Long> {

	List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary);

	Long countBySalaryGroup(SalaryGroup salaryGroup);

}
