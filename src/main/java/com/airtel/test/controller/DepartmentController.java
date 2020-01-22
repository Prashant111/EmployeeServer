package com.airtel.test.controller;

import com.airtel.test.entity.Department;
import com.airtel.test.entity.User;
import com.airtel.test.service.DepartmentService;
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
public class DepartmentController extends AbstractController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    //Create Department
    @RequestMapping(method = RequestMethod.POST, value = "/department")
    public ResponseEntity createDepartment(HttpSession session, @RequestBody Department department) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        if (!departmentService.saveDepartment(this, session, department)) {
            String responseMessage = "Department with id " + department.getName() + " already exist in the department";
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.BAD_REQUEST, responseMessage);
        }
        return StandardResponse.getSuccessfullyCreatedResponse();
    }


    //Get all departments
    @RequestMapping(method = RequestMethod.GET, value = "/departments")
    public ResponseEntity getAllDepartments(HttpSession session) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        List<Department> allDepartments = departmentService.getAllDepartments();
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, allDepartments);
    }

    //Get Department By name
    @RequestMapping(method = RequestMethod.GET, value = "/department/{name}")
    public ResponseEntity getDepartmentByName(HttpSession session, @PathVariable("name") String name) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        Department department = departmentService.getDepartmentByName(name);
        if (department == null) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "Department name does not exist.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, department);
    }

    //Get Department By user
    @RequestMapping(method = RequestMethod.GET, value = "/departmentsByUser/{user}")
    public ResponseEntity getDepartmentsByUser(HttpSession session, @PathVariable("user") String userName) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        User user = userService.getByUserName(userName);
        if (user == null) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "User name does not exist.");
        }
        List<Department> departments = departmentService.getDepartmentByCreatedBy(userName);
        if(GeneralUtils.isListBlank(departments)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "No department found for the user.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, departments);
    }

    //Update Department
    @RequestMapping(method = RequestMethod.PUT, value = "/department/{name}")
    public ResponseEntity updateDepartmentOfName(HttpSession session, @PathVariable("name") String name, @RequestBody Department department) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        department.setName(name);
        if (!departmentService.updateDepartment(this, session, department)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "Department name does not exist.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, "Department updated successfully");
    }

    //Delete Department
    @RequestMapping(method = RequestMethod.DELETE, value = "/department/{name}")
    public ResponseEntity deleteDepartment(HttpSession session, @PathVariable("name") String name) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        if (!departmentService.deleteDepartment(name)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "Department name does not exist.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, "Department deleted successfully");
    }
}
