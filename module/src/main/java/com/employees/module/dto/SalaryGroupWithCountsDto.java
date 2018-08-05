package com.employees.module.dto;

import java.math.BigDecimal;

import com.employees.module.entity.SalaryGroup;

public class SalaryGroupWithCountsDto {

	private String salaryGroupName;

	private Long count;

	public static SalaryGroupWithCountsDto createDto(SalaryGroup salaryGroup, Long counter) {
		SalaryGroupWithCountsDto salaryGroupDto = new SalaryGroupWithCountsDto();
		salaryGroupDto.setSalaryGroupName(printPrettyName(salaryGroup.getFromValue(), salaryGroup.getToValue()));
		salaryGroupDto.setCount(counter);
		return salaryGroupDto;
	}

	private static String printPrettyName(BigDecimal from, BigDecimal to) {
		StringBuilder sb = new StringBuilder();
		sb.append(from).append(" - ").append(to);
		return sb.toString();
	}

	public String getSalaryGroupName() {
		return salaryGroupName;
	}

	public void setSalaryGroupName(String salaryGroupName) {
		this.salaryGroupName = salaryGroupName;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
