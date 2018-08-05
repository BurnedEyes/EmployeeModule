package com.employees.module.dao.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employees.module.dao.EmployeeDao;
import com.employees.module.dao.SalaryGroupDao;
import com.employees.module.entity.Employee;
import com.employees.module.entity.SalaryGroup;
import com.employees.module.entity.builder.EmployeeBuilder;

@Service
public class SampleDataCreator {

	private static EmployeeDao employeeDao;
	private static SalaryGroupDao salaryGroupDao;

	private EmployeeDao employeeRepository;
	private SalaryGroupDao salaryGroupRepository;

	public static final SalaryGroup SG_0_100 = new SalaryGroup(BigDecimal.ZERO, BigDecimal.valueOf(100));
	public static final SalaryGroup SG_100_1000 = new SalaryGroup(BigDecimal.valueOf(100), BigDecimal.valueOf(1000));
	public static final SalaryGroup SG_1000_5000 = new SalaryGroup(BigDecimal.valueOf(1000), BigDecimal.valueOf(5000));
	public static final SalaryGroup SG_5000_MAX = new SalaryGroup(BigDecimal.valueOf(5000),
			BigDecimal.valueOf(Integer.MAX_VALUE));

	public static final Employee JOHN_DOE = EmployeeBuilder.newBuilder().withFirstName("John").withLastName("Doe")
			.withSalary(new BigDecimal(8500)).withSalaryGroup(SG_0_100).build();
	public static final Employee BILL_GATES = EmployeeBuilder.newBuilder().withFirstName("Bill").withLastName("Gates")
			.withSalary(new BigDecimal(9999999)).withSalaryGroup(SG_1000_5000).build();
	public static final Employee ANONYMOUS = EmployeeBuilder.newBuilder().withLastName("anonymous")
			.withSalary(new BigDecimal(500)).withSalaryGroup(SG_1000_5000).build();
	public static final Employee WITHOUT_GROUP = EmployeeBuilder.newBuilder().withFirstName("New").withLastName("One")
			.withSalary(new BigDecimal(1001)).build();

	@Autowired
	public SampleDataCreator(EmployeeDao employeeRepository, SalaryGroupDao salaryGroupRepository) {
		this.employeeRepository = employeeRepository;
		this.salaryGroupRepository = salaryGroupRepository;
	}

	@PostConstruct
	private void init() {
		employeeDao = employeeRepository;
		salaryGroupDao = salaryGroupRepository;
	}

	public static void addEmployees() {
		employeeDao.saveAll(allEmployees());
	}

	public static void addSalaryGroups() {
		salaryGroupDao.saveAll(allSalaryGroups());
	}

	public static List<SalaryGroup> allSalaryGroups() {
		return Arrays.asList(SG_0_100, SG_100_1000, SG_1000_5000, SG_5000_MAX);
	}

	public static List<Employee> allEmployees() {
		return Arrays.asList(JOHN_DOE, BILL_GATES, ANONYMOUS, WITHOUT_GROUP);
	}

}
