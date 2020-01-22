package com.airtel.test.controller.mongo;

import com.airtel.test.controller.AbstractController;
import com.airtel.test.entity.User;
import com.airtel.test.entity.mongodb.DepartmentMongo;
import com.airtel.test.repository.UserRepository;
import com.airtel.test.repository.mongo.DepartmentMongoRepository;
import com.airtel.test.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mongo")
public class DepartmentMongoController extends AbstractController {

    @Autowired
    private DepartmentMongoRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;

    //Create DepartmentMongo
    @RequestMapping(method = RequestMethod.POST, value = "/department")
    public ResponseEntity createDepartment(HttpSession session, @RequestBody DepartmentMongo department) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        DepartmentMongo department1 = departmentRepository.findByName(department.getName()).orElse(null);
        if (department1 != null) {
            String responseMessage = "DepartmentMongo with id " + department.getName() + " already exist in the department";
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.BAD_REQUEST, responseMessage);
        }
        department.setCreatedBy(getUserIdFromSession(session));
        department.setLastModifiedBy(getUserIdFromSession(session));
        departmentRepository.save(department);
        return StandardResponse.getSuccessfullyCreatedResponse();
    }


    //Get all departments
    @RequestMapping(method = RequestMethod.GET, value = "/departments")
    public ResponseEntity getAllDepartmentss(HttpSession session) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        List<DepartmentMongo> allDepartments = new ArrayList<>();
        departmentRepository.findAll().forEach(allDepartments::add);
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, allDepartments);
    }

    //Get DepartmentMongo By name
    @RequestMapping(method = RequestMethod.GET, value = "/department/{name}")
    public ResponseEntity getDepartmentByName(HttpSession session, @PathVariable("name") String name) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        DepartmentMongo department1 = departmentRepository.findByName(name).orElse(null);
        if (department1 == null) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "DepartmentMongo name does not exist.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, departmentRepository.findByName(name));
    }

    //Get DepartmentMongo By user
    @RequestMapping(method = RequestMethod.GET, value = "/departmentsByUser/{user}")
    public ResponseEntity getDepartmentsByUser(HttpSession session, @PathVariable("user") String user) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        User user1 = userRepository.findById(user).orElse(null);
        if (user1 == null) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "User name does not exist.");
        }
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, departmentRepository.findByCreatedBy(user));
    }

    //Update DepartmentMongo
    @RequestMapping(method = RequestMethod.PUT, value = "/department/{name}")
    public ResponseEntity updateDepartmentOfName(HttpSession session, @PathVariable("name") String name, @RequestBody DepartmentMongo department) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        DepartmentMongo department1 = departmentRepository.findByName(name).orElse(null);
        if (department1 == null) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "DepartmentMongo name does not exist.");
        }
        department.setName(department1.getName());
        department.setLastModifiedBy(getUserIdFromSession(session));
        departmentRepository.save(department);
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, "DepartmentMongo updated successfully");
    }

    //Delete DepartmentMongo
    @RequestMapping(method = RequestMethod.DELETE, value = "/department/{name}")
    public ResponseEntity deleteDepartment(HttpSession session, @PathVariable("name") String name) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.requestToSigninRequest();
        }
        DepartmentMongo department1 = departmentRepository.findByName(name).orElse(null);
        if (department1 == null) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.EXPECTATION_FAILED, "DepartmentMongo name does not exist.");
        }
        departmentRepository.delete(department1);
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, "DepartmentMongo deleted successfully");
    }
}
