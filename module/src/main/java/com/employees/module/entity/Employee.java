package com.employees.module.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Employee extends AbstractEntity {

	@Column(unique = true, name = "CODE")
	private String code;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "SECOND_NAME")
	private String lastName;

	@Embedded
	private Address address;

	@Column(name = "SALARY")
	private BigDecimal salary;

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	private SalaryGroup salaryGroup;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Address getAddress() {
		return address;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public SalaryGroup getSalaryGroup() {
		return salaryGroup;
	}

	public void setSalaryGroup(SalaryGroup salaryGroup) {
		this.salaryGroup = salaryGroup;
	}

}
