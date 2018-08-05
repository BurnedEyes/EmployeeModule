package com.employees.module.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportGeneratorTest {

	ReportGenerator underTest;

	@Mock
	EmployeeService employeeService;

	@Before
	public void setUp() {
		underTest = new ReportGenerator(employeeService);
	}

	@Test
	public void shouldGenerateReport() {
		// TODO:
		underTest.generateReport();
	}

}
