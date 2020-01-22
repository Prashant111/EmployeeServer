package com.airtel.test.controller;

import com.airtel.test.entity.Department;
import com.airtel.test.entity.Salary;
import com.airtel.test.service.SalaryService;
import com.airtel.test.service.UserService;
import com.airtel.test.util.GeneralUtils;
import com.airtel.test.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class SalaryController extends AbstractController {
    @Autowired
    private SalaryService salaryService;
    @Autowired
    private UserService userService;

    //Create Salary
    @RequestMapping(method = RequestMethod.POST, value = "/salary")
    public ResponseEntity createSalary(HttpSession session, @RequestBody Salary salary) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        if (!salaryService.save(this, session, salary)) {
            String responseMessage = "Salary with id " + salary.getSalaryPlan() + " already exist in the salary";
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.BAD_REQUEST, responseMessage);
        }
        return StandardResponse.getSuccessfullyCreatedResponse();
    }

    @Override
    public String getUserIdFromSession(HttpSession session) {
        return super.getUserIdFromSession(session);
    }

    //Get all salaries
    @RequestMapping(method = RequestMethod.GET, value = "/salaries")
    public ResponseEntity getAllSalaries(HttpSession session) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, salaryService.getAll());
    }

    //Get Salary By plan
    @RequestMapping(method = RequestMethod.GET, value = "/salary/{plan}")
    public ResponseEntity getSalaryByPlan(HttpSession session, @PathVariable("plan") String plan) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        Salary salary = salaryService.getByPlan(plan);
        if (salary == null) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "Salary plan does not exist.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, salary);
    }


    //Get Salaries By user
    @RequestMapping(method = RequestMethod.GET, value = "/salariesByUser/{user}")
    public ResponseEntity getSalariesByUser(HttpSession session, @PathVariable("user") String userName) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        if (!userService.doesUserWithUserIdExist(userName)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "User name does not exist.");
        }
        List<Salary> salaries = salaryService.getSalaryByCreatedBy(userName);
        if(GeneralUtils.isListBlank(salaries)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "No salary found for the user.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, salaries);
    }

    //Update Salary
    @RequestMapping(method = RequestMethod.PUT, value = "/salary/{plan}")
    public ResponseEntity updateSalaryOfPlan(HttpSession session, @PathVariable("plan") String plan, @RequestBody Salary salary) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        salary.setSalaryPlan(plan);
        if (!salaryService.update(this,session,salary)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "Salary plan does not exist.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, "Salary updated successfully");
    }

    //Delete Salary
    @RequestMapping(method = RequestMethod.DELETE, value = "/salary/{plan}")
    public ResponseEntity deleteSalary(HttpSession session, @PathVariable("plan") String plan) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        if (!salaryService.delete(plan)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "Salary plan does not exist.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, "Salary deleted successfully");
    }
}
