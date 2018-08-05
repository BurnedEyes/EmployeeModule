package com.employees.module.entity.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.employees.module.entity.Employee;
import com.employees.module.entity.SalaryGroup;

public class EmployeeBuilder {

	private String firstName;
	private String lastName;
	private BigDecimal salary;
	private SalaryGroup salaryGroup;
	private LocalDateTime lastModifiedDate;

	public EmployeeBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public EmployeeBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public EmployeeBuilder withSalary(BigDecimal salary) {
		this.salary = salary;
		return this;
	}

	public EmployeeBuilder withSalaryGroup(SalaryGroup group) {
		this.salaryGroup = group;
		return this;
	}

	public EmployeeBuilder withLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
		return this;
	}

	public Employee build() {
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setSalary(salary);
		employee.setSalaryGroup(salaryGroup);
		employee.setLastModifiedDateTime(lastModifiedDate);
		return employee;
	}

	public static EmployeeBuilder newBuilder() {
		return new EmployeeBuilder();
	}

}
