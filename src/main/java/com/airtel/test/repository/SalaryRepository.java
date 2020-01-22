package com.airtel.test.repository;

import com.airtel.test.entity.Salary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends CrudRepository<Salary, String> {
    public Optional<List<Salary>> findByCreatedBy(String user);
}