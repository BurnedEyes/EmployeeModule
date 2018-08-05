package com.employees.module.service;

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.employees.module.dao.EmployeeDao;
import com.employees.module.dao.SalaryGroupDao;
import com.employees.module.dao.utils.SampleDataCreator;
import com.employees.module.dto.SalaryGroupWithCountsDto;
import com.employees.module.entity.Employee;
import com.employees.module.entity.SalaryGroup;
import com.employees.module.entity.builder.EmployeeBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceImplTest {
	@Mock
	SalaryGroupDao salaryGroupDao;
	@Mock
	EmployeeDao employeeDao;

	EmployeeServiceImpl underTest;

	@Before
	public void setUp() {
		underTest = new EmployeeServiceImpl(salaryGroupDao, employeeDao);
		when(salaryGroupDao.findAll()).thenReturn(SampleDataCreator.allSalaryGroups());
		when(employeeDao.countBySalaryGroup(any())).thenAnswer(inv -> {
			Object argument = inv.getArguments()[0];
			if (argument.equals(SampleDataCreator.SG_0_100)) {
				return 1l;
			} else if (argument.equals(SampleDataCreator.SG_1000_5000)) {
				return 2l;
			} else {
				return 0l;
			}
		});
	}

	@Test
	public void shouldCalculateEmployeesAccordingToSalaryGroups() {
		List<SalaryGroupWithCountsDto> calculateEmployeesAccordingToSalaryGroups = underTest
				.calculateEmployeesAccordingToSalaryGroups();

		assertThat(calculateEmployeesAccordingToSalaryGroups, Matchers.hasSize(4));
		assertThat(calculateEmployeesAccordingToSalaryGroups, hasItem(Matchers
				.both(hasProperty("salaryGroupName", equalTo("0 - 100"))).and(hasProperty("count", equalTo(1l)))));
		assertThat(calculateEmployeesAccordingToSalaryGroups, hasItem(Matchers
				.both(hasProperty("salaryGroupName", equalTo("100 - 1000"))).and(hasProperty("count", equalTo(0l)))));
		assertThat(calculateEmployeesAccordingToSalaryGroups, hasItem(Matchers
				.both(hasProperty("salaryGroupName", equalTo("1000 - 5000"))).and(hasProperty("count", equalTo(2l)))));
	}

	@Test
	public void shouldAssignEmployeeWithoutSalaryGroup() {
		Employee employee = EmployeeBuilder.newBuilder().withFirstName("New").withLastName("One")
				.withSalary(new BigDecimal(1001)).build();
		when(employeeDao.findAll()).thenReturn(Arrays.asList(employee));
		when(salaryGroupDao.findByFromValueLessThanAndToValueGreaterThanEqual(any(), any()))
				.thenReturn(Optional.of(SampleDataCreator.SG_1000_5000));

		underTest.reassignEmployeesToAppropriateSalaryGroups(LocalDateTime.now());

		assertThat(employee.getSalaryGroup(), equalTo(SampleDataCreator.SG_1000_5000));
		verify(employeeDao).save(employee);
	}

	@Test
	public void shouldNotReassignEmployeeWithSalaryGroupIfNotModifiedSinceLastTime() {
		SalaryGroup salaryGroup = new SalaryGroup();
		salaryGroup.setLastModifiedDateTime(LocalDateTime.now().minusHours(1));
		Employee employee = EmployeeBuilder.newBuilder().withFirstName("New").withLastName("One")
				.withSalary(new BigDecimal(1001)).withSalaryGroup(salaryGroup)
				.withLastModifiedDate(LocalDateTime.now().minusHours(1)).build();
		when(employeeDao.findAll()).thenReturn(Arrays.asList(employee));
		when(salaryGroupDao.findAll()).thenReturn(Arrays.asList(salaryGroup));

		underTest.reassignEmployeesToAppropriateSalaryGroups(LocalDateTime.now());

		assertThat(employee.getSalaryGroup(), equalTo(salaryGroup));
		verify(salaryGroupDao, never()).findByFromValueLessThanAndToValueGreaterThanEqual(any(), any());
		verify(employeeDao, never()).save(any());

	}

	@Test
	public void shouldReassignEmployeeWithSalaryGroupIfNeeded() {
		SalaryGroup salaryGroup = new SalaryGroup();
		salaryGroup.setLastModifiedDateTime(LocalDateTime.now().plusHours(1));
		Employee employee = EmployeeBuilder.newBuilder().withFirstName("New").withLastName("One")
				.withSalary(new BigDecimal(1001)).withSalaryGroup(salaryGroup).withLastModifiedDate(LocalDateTime.now())
				.build();
		when(employeeDao.findAll()).thenReturn(Arrays.asList(employee));
		when(salaryGroupDao.findAll()).thenReturn(Arrays.asList(salaryGroup));
		when(salaryGroupDao.findByFromValueLessThanAndToValueGreaterThanEqual(any(), any()))
				.thenReturn(Optional.of(SampleDataCreator.SG_1000_5000));

		underTest.reassignEmployeesToAppropriateSalaryGroups(LocalDateTime.now());

		assertThat(employee.getSalaryGroup(), equalTo(SampleDataCreator.SG_1000_5000));
		verify(employeeDao).save(employee);
	}
}
