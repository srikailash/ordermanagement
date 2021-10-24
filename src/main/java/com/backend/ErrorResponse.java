package com.backend;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javassist.NotFoundException;

/*
Document possible Exceptions
*/

public class ErrorResponse {

    private String devMessage;
    // private String userMessage;
    private HttpStatus code;


    public ErrorResponse(Exception ex) {
        super();

        this.code = HttpStatus.INTERNAL_SERVER_ERROR;
        this.devMessage = ex.getMessage();

        //Client errors begin
        if(ex.getClass() == MethodArgumentTypeMismatchException.class) {
            this.code = HttpStatus.BAD_REQUEST;
            // this.userMessage = "Invalid input parameters";
        }

        if(ex.getClass() == MissingRequestCookieException.class) {
            this.code = HttpStatus.BAD_REQUEST;
            // this.userMessage = "Invalid request headers";
        }

        if(ex.getClass() == MissingRequestHeaderException.class) {
            this.code = HttpStatus.BAD_REQUEST;            
        }

        if(ex.getClass() == HttpRequestMethodNotSupportedException.class) {
            this.code = HttpStatus.BAD_REQUEST;
        }
        
        if(ex.getClass() == AccessDeniedException.class) {
            this.code = HttpStatus.UNAUTHORIZED;
        }
        
        if(ex.getClass() == NotFoundException.class) {
            this.code = HttpStatus.BAD_REQUEST;
        }
    }

    public String getDevMessage() {

        return devMessage;
    }

    public HttpStatus getCode() {

        return code;
    }

}
