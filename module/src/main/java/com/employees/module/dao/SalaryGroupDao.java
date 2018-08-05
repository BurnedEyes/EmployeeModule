package com.employees.module.dao;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.employees.module.entity.SalaryGroup;

public interface SalaryGroupDao extends CrudRepository<SalaryGroup, Long> {
	Optional<SalaryGroup> findByFromValueLessThanAndToValueGreaterThanEqual(BigDecimal salary, BigDecimal salary2);
}
