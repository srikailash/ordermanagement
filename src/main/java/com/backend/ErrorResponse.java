package com.backend;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class ErrorResponse {

    private String devMessage;
    // private String userMessage;
    private HttpStatus code;


    public ErrorResponse(Exception ex) {
        super();

        this.code = HttpStatus.INTERNAL_SERVER_ERROR;
        this.devMessage = ex.getLocalizedMessage();

        //Client errors begin
        if(ex.getClass() == MethodArgumentTypeMismatchException.class) {
            this.code = HttpStatus.BAD_REQUEST;
            // this.userMessage = "Invalid input parameters";
        }

        if(ex.getClass() == MissingRequestCookieException.class) {
            this.code = HttpStatus.BAD_REQUEST;
            // this.userMessage = "Invalid request headers";
        }

        if(ex.getMessage() == "Unauthorized access") {
            this.code = HttpStatus.UNAUTHORIZED;
        }

        //Client errors end

        //Server errors begin

        //Server errors end

    }

    public String getDevMessage() {

        return devMessage;
    }

    public HttpStatus getCode() {

        return code;
    }

}
