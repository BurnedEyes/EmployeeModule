package com.employees.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.employees.module.dao.utils.SampleDataCreator;

@SpringBootApplication
@EnableScheduling
public class ModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleApplication.class, args);
		SampleDataCreator.addSalaryGroups();
		SampleDataCreator.addEmployees();
	}
}
