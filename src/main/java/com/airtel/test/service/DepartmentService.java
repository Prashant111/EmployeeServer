package com.airtel.test.service;

import com.airtel.test.controller.AbstractController;
import com.airtel.test.entity.Department;
import com.airtel.test.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;


    public boolean saveDepartment(AbstractController controller, HttpSession session, Department department) {
        if (!doesDepartmentExistById(department.getName())) {
            department.setCreatedBy(controller.getUserIdFromSession(session));
            department.setLastModifiedBy(controller.getUserIdFromSession(session));
            departmentRepository.save(department);
            return true;
        }
        return false;
    }

    public boolean doesDepartmentExistById(String departmentName) {
        Department department = departmentRepository.findById(departmentName).orElse(null);
        return department != null;
    }

    public List<Department> getAllDepartments() {
        List<Department> allDepartments = new ArrayList<>();
        departmentRepository.findAll().forEach(allDepartments::add);
        return allDepartments;
    }

    public Department getDepartmentByName(String departmentName) {
        return departmentRepository.findById(departmentName).orElse(null);
    }

    public List<Department> getDepartmentByCreatedBy(String user) {
        return departmentRepository.findByCreatedBy(user).orElse(null);
    }

    public boolean updateDepartment(AbstractController controller, HttpSession session, Department department) {
        Department departmentFromDb = getDepartmentByName(department.getName());
        if (departmentFromDb != null) {
            department.setCreatedBy(departmentFromDb.getCreatedBy());
            department.setLastModifiedBy(controller.getUserIdFromSession(session));
            departmentRepository.save(department);
            return true;
        }
        return false;
    }

    public boolean deleteDepartment(String departmentName) {
        Department department = getDepartmentByName(departmentName);
        if (department != null) {
            departmentRepository.delete(department);
            return true;
        }
        return false;
    }
}
