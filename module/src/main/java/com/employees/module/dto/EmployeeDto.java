package com.employees.module.dto;

import java.math.BigDecimal;

import com.employees.module.entity.Address;
import com.employees.module.entity.Employee;

public class EmployeeDto {
	private String code;

	private String firstName;

	private String lastName;

	private String address;

	private BigDecimal salary;

	public static EmployeeDto toDto(Employee employee) {
		EmployeeDto result = new EmployeeDto();
		result.setAddress(printPrettyAddress(employee.getAddress()));
		result.setCode(employee.getCode());
		result.setFirstName(employee.getFirstName());
		result.setLastName(employee.getLastName());
		result.setSalary(employee.getSalary());
		return result;
	}

	private static String printPrettyAddress(Address address) {
		StringBuilder sb = new StringBuilder();
		if (address != null) {
			sb.append(address.getFirstLine()).append("\n").append(address.getSecondLine());
		}
		return sb.toString();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
