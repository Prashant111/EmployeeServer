package com.airtel.test.repository;

import com.airtel.test.entity.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends CrudRepository<Department, String> {
    public Optional<List<Department>> findByCreatedBy(String name);
}
