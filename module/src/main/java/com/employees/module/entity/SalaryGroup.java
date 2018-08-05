package com.employees.module.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class SalaryGroup extends AbstractEntity {

	public SalaryGroup() {
		super();
	}

	public SalaryGroup(BigDecimal fromValue, BigDecimal toValue) {
		this.fromValue = fromValue;
		this.toValue = toValue;
	}

	@OneToMany(mappedBy = "salaryGroup")
	private List<Employee> employee;

	@NotNull
	@Column(name = "TO_VALUE")
	private BigDecimal toValue;
	@Column(name = "FROM_VALUE")
	@NotNull
	private BigDecimal fromValue;

	public List<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}

	public BigDecimal getToValue() {
		return toValue;
	}

	public void setToValue(BigDecimal toValue) {
		this.toValue = toValue;
	}

	public BigDecimal getFromValue() {
		return fromValue;
	}

	public void setFromValue(BigDecimal fromValue) {
		this.fromValue = fromValue;
	}

}
