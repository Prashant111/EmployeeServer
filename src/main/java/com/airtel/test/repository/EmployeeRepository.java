package com.airtel.test.repository;

import com.airtel.test.entity.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    public Optional<List<Employee>> findByDepartmentId(String departmentName);

    public Optional<List<Employee>> findBySalaryPlan(String salaryPlan);

    public Optional<List<Employee>> findByName(String name);

    public Optional<List<Employee>> findByCreatedBy(String user);
}
