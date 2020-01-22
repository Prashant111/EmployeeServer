package com.airtel.test.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StandardResponse {
    public static enum SigninResult {
        SUCCESS, USER_NOT_EXIST, WRONG_PASSWORD
    }

    public static ResponseEntity getSuccessfulResponse() {
        return getSuccessfulResponse(HttpStatus.OK, null);
    }

    public static ResponseEntity getSuccessfulResponse(HttpStatus httpStatus, Object object) {
        Object object1 = object != null ? object : "Success";
        HttpStatus httpStatus1 = httpStatus != null ? httpStatus : HttpStatus.OK;
        return new ResponseEntity<>(object1, httpStatus1);
    }

    public static ResponseEntity getUnsuccessfulResponse() {
        return getUnsuccessfulResponse(HttpStatus.BAD_REQUEST, null);
    }

    public static ResponseEntity getUnsuccessfulResponse(HttpStatus httpStatus, Object object) {
        Object object1 = object != null ? object : "Failed";
        HttpStatus httpStatus1 = httpStatus != null ? httpStatus : HttpStatus.EXPECTATION_FAILED;
        return new ResponseEntity<>(object, httpStatus);
    }

    public static ResponseEntity getSuccessfullyCreatedResponse() {
        return new ResponseEntity<>("Successfully created", HttpStatus.CREATED);
    }

    public static ResponseEntity requestToSigninRequest() {
        return getUnsuccessfulResponse(HttpStatus.FORBIDDEN, "Please signin first");
    }

}
