package com.airtel.test.service;

import com.airtel.test.controller.AbstractController;
import com.airtel.test.entity.Employee;
import com.airtel.test.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean doesEmployeeExistById(String employeeId) {
        return employeeRepository.findById(employeeId).orElse(null) != null;
    }

    public boolean save(AbstractController controller, HttpSession session, Employee employee) {
        if (!doesEmployeeExistById(employee.getEmployeeId())) {
            employee.setCreatedBy(controller.getUserIdFromSession(session));
            employee.setLastModifiedBy(controller.getUserIdFromSession(session));
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    public List<Employee> getAll() {
        List<Employee> allEmployees = new ArrayList<>();
        employeeRepository.findAll().forEach(allEmployees::add);
        return allEmployees;
    }

    public Employee getById(String employeeName) {
        return employeeRepository.findById(employeeName).orElse(null);
    }

    public List<Employee> getByDepartment(String department) {
        return employeeRepository.findByDepartmentId(department).orElse(null);
    }

    public List<Employee> getByName(String name) {
        return employeeRepository.findByName(name).orElse(null);
    }

    public List<Employee> getBySalaryPlan(String salaryPlan) {
        return employeeRepository.findBySalaryPlan(salaryPlan).orElse(null);
    }

    public List<Employee> getEmployeeByCreatedBy(String user) {
        return employeeRepository.findByCreatedBy(user).orElse(null);
    }

    public boolean update(AbstractController controller, HttpSession session, Employee employee) {
        Employee employeeFromDb = getById(employee.getEmployeeId());
        if (employeeFromDb != null) {
            employee.setCreatedBy(employeeFromDb.getCreatedBy());
            employee.setLastModifiedBy(controller.getUserIdFromSession(session));
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    public boolean delete(String employeePlan) {
        Employee employee = getById(employeePlan);
        if (employee != null) {
            employeeRepository.delete(employee);
            return true;
        }
        return false;
    }
}
