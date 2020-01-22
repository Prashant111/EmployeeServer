package com.airtel.test.controller;

import com.airtel.test.entity.Employee;
import com.airtel.test.service.DepartmentService;
import com.airtel.test.service.EmployeeService;
import com.airtel.test.service.SalaryService;
import com.airtel.test.service.UserService;
import com.airtel.test.util.GeneralUtils;
import com.airtel.test.util.StandardResponse;
import com.airtel.test.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class EmployeeController extends AbstractController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SalaryService salaryService;
    @Autowired
    private UserService userService;

    //Create Employee
    @PostMapping("/employee")
    public ResponseEntity createEmployee(HttpSession session, @RequestBody Employee employee) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        boolean isSuccess = true;
        String responseMessage = "";
        if (!departmentService.doesDepartmentExistById(employee.getDepartmentId())) {
            responseMessage += "Department with id " + employee.getDepartmentId() + " does not exist in the database\n";
            isSuccess = false;
        }
        if (!salaryService.doesSalaryExistByPlan(employee.getSalaryPlan())) {
            responseMessage += "Salary with salary plan " + employee.getSalaryPlan() + " does not exist in the database\n";
            isSuccess = false;
        }
        if (!(isSuccess && employeeService.save(this, session, employee))) {
            responseMessage = StringUtils.isNotBlank(responseMessage) ? responseMessage : "Employee already exist.";
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, responseMessage);
        }
        return StandardResponse.getSuccessfullyCreatedResponse();
    }

    //Get All Employees
    @RequestMapping(method = RequestMethod.GET, value = "/employees")
    public ResponseEntity getAllEmployees(HttpSession session) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, employeeService.getAll());
    }

    //Get Employee By Id
    @RequestMapping(method = RequestMethod.GET, value = "/employee/{employeeId}")
    public ResponseEntity getEmployeeById(HttpSession session, @PathVariable("employeeId") String employeeId) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "Employee does not exist.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, employee);
    }

    //Get Employees By Department
    @RequestMapping(method = RequestMethod.GET, value = "/employeesByDepartment/{department}")
    public ResponseEntity getEmployeeByDepartment(HttpSession session, @PathVariable("department") String department) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        if (!departmentService.doesDepartmentExistById(department)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "Department " + department + " does not exist.");
        }
        List<Employee> employees = employeeService.getByDepartment(department);
        if (GeneralUtils.isListBlank(employees)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "No Employee exist for the given department");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, employees);
    }

    //Get Employees By Name
    @RequestMapping(method = RequestMethod.GET, value = "/employeesByName/{name}")
    public ResponseEntity getEmployeeByName(HttpSession session, @PathVariable("name") String name) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        List<Employee> employees = employeeService.getByName(name);
        if (GeneralUtils.isListBlank(employees)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "No Employee exist with name " + name + " in the database");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, employees);
    }

    //Get Employees By Salary
    @RequestMapping(method = RequestMethod.GET, value = "/employeesBySalaryPlan/{salaryPlan}")
    public ResponseEntity getEmployeeBySalaryPlan(HttpSession session, @PathVariable("salaryPlan") String salaryPlan) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        if (!salaryService.doesSalaryExistByPlan(salaryPlan)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "Salary does not exist for salary plan " + salaryPlan);
        }
        List<Employee> employees = employeeService.getBySalaryPlan(salaryPlan);
        if (GeneralUtils.isListBlank(employees)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "No Employee exist for given salary plan");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, employees);
    }

    //Get Employee By user
    @RequestMapping(method = RequestMethod.GET, value = "/employeesByUser/{user}")
    public ResponseEntity getEmployeesByUser(HttpSession session, @PathVariable("user") String user) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        if (!userService.doesUserWithUserIdExist(user)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "User name does not exist.");
        }
        List<Employee> employees = employeeService.getEmployeeByCreatedBy(user);
        if (GeneralUtils.isListBlank(employees)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "No employees found for the user.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, employees);
    }

    //Update Employee
    @RequestMapping(method = RequestMethod.PUT, value = "/employee/{employeeId}")
    public ResponseEntity updateEmployee(HttpSession session, @PathVariable("employeeId") String employeeId, @RequestBody Employee employee) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        String responseMessage = "";
        boolean isSuccess = true;
        if (!departmentService.doesDepartmentExistById(employee.getDepartmentId())) {
            responseMessage += "Department with id " + employee.getDepartmentId() + " does not exist in the database\n";
            isSuccess = false;
        }
        if (!salaryService.doesSalaryExistByPlan(employee.getSalaryPlan())) {
            responseMessage += "Salary with salary plan " + employee.getSalaryPlan() + " does not exist in the database\n";
            isSuccess = false;
        }
        employee.setEmployeeId(employeeId);
        if (!isSuccess) {
            responseMessage = StringUtils.isNotBlank(responseMessage) ? responseMessage : "Falied";
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, responseMessage);
        } else if (!employeeService.update(this, session, employee)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "Employee does not exist.");
        } else {
            responseMessage = "Employee successfully updated";
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, responseMessage);
    }

    //Delete Employee
    @RequestMapping(method = RequestMethod.DELETE, value = "/employee/{employeeId}")
    public ResponseEntity deleteEmployee(HttpSession session, @PathVariable("employeeId") String employeeId) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        if (!employeeService.delete(employeeId)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "Employee does not exist.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, "Employee deleted successfully");
    }

}