package com.airtel.test.service;

import com.airtel.test.controller.AbstractController;
import com.airtel.test.entity.Salary;
import com.airtel.test.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Component
public class SalaryService {
    @Autowired
    private SalaryRepository salaryRepository;

    public boolean save(AbstractController controller, HttpSession session, Salary salary) {
        if (!doesSalaryExistByPlan(salary.getSalaryPlan())) {
            salary.setCreatedBy(controller.getUserIdFromSession(session));
            salary.setLastModifiedBy(controller.getUserIdFromSession(session));
            salaryRepository.save(salary);
            return true;
        }
        return false;
    }

    public boolean doesSalaryExistByPlan(String salaryName) {
        Salary salary = salaryRepository.findById(salaryName).orElse(null);
        return salary != null;
    }

    public List<Salary> getAll() {
        List<Salary> allSalarys = new ArrayList<>();
        salaryRepository.findAll().forEach(allSalarys::add);
        return allSalarys;
    }

    public Salary getByPlan(String salaryName) {
        return salaryRepository.findById(salaryName).orElse(null);
    }

    public List<Salary> getSalaryByCreatedBy(String user) {
        return salaryRepository.findByCreatedBy(user).orElse(null);
    }

    public boolean update(AbstractController controller, HttpSession session, Salary salary) {
        Salary salaryFromDb = getByPlan(salary.getSalaryPlan());
        if (salaryFromDb != null) {
            salary.setCreatedBy(salaryFromDb.getCreatedBy());
            salary.setLastModifiedBy(controller.getUserIdFromSession(session));
            salaryRepository.save(salary);
            return true;
        }
        return false;
    }

    public boolean delete(String salaryPlan) {
        Salary salary = getByPlan(salaryPlan);
        if (salary != null) {
            salaryRepository.delete(salary);
            return true;
        }
        return false;
    }
}
