package com.employees.module.service;

import java.time.LocalDateTime;
import java.util.List;

import com.employees.module.dto.SalaryGroupWithCountsDto;

public interface EmployeeService {

	/**
	 * group and list employees according to salary groups
	 *
	 * @return list of all salary groups with counter of employees included
	 */
	List<SalaryGroupWithCountsDto> calculateEmployeesAccordingToSalaryGroups();

	/**
	 * assign or reassign each employee to appropriate salary group according to
	 * his salary.
	 *
	 * @param lastTime
	 *            - last time the assignment was valid
	 */
	void reassignEmployeesToAppropriateSalaryGroups(LocalDateTime lastTime);
}