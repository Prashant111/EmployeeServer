package com.airtel.test.controller;


import com.airtel.test.entity.User;
import com.airtel.test.service.UserService;
import com.airtel.test.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity showUsers() {
        return StandardResponse.getSuccessfulResponse(HttpStatus.OK, userService.getAll());
    }

    @PostMapping("/signup")
    public ResponseEntity signup(HttpSession session, @RequestBody User user) {
        if (super.isUserSignedIn(session)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.FORBIDDEN, "Please signout first");
        }
        if (!userService.signUp(this, session, user)) {
            return StandardResponse.getUnsuccessfulResponse(BAD_REQUEST, "User already exist for userName :" + user.getUserName());
        } else {
            return StandardResponse.getSuccessfullyCreatedResponse();
        }
    }

    @GetMapping("/signin")
    public ResponseEntity signin(HttpSession session, @RequestParam(name = "userName") String userName, @RequestParam(name = "password") String password) {
        if (super.isUserSignedIn(session)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.FORBIDDEN, "Please sign out first");
        }
        StandardResponse.SigninResult signinResult = userService.signIn(this, session, userName, password);
        if (signinResult == StandardResponse.SigninResult.USER_NOT_EXIST) {
            return StandardResponse.getUnsuccessfulResponse(BAD_REQUEST, "User does not exist for userName :" + userName);
        } else if (signinResult == StandardResponse.SigninResult.WRONG_PASSWORD) {
            return StandardResponse.getUnsuccessfulResponse(BAD_REQUEST, "Incorrect password for userName :" + userName);
        } else {
            super.saveUserInSession(session, userName);
            return StandardResponse.getSuccessfulResponse(HttpStatus.OK, "Signed in Successfully");
        }
    }

    @GetMapping("/signout")
    public ResponseEntity signout(HttpSession session) {
        if (!isUserSignedIn(session)) {
            return StandardResponse.getUnsuccessfulResponse(HttpStatus.FORBIDDEN, "You have already signed out");
        }
        userService.signOut(this, session);
        return StandardResponse.getUnsuccessfulResponse(HttpStatus.OK, "You signed out successfully");
    }
}
