package com.thoughtworks.springbootemployee.config;

import com.thoughtworks.springbootemployee.Exception.IllegalUpdateCompanyException;
import com.thoughtworks.springbootemployee.Exception.IllegalUpdateEmployeeException;
import com.thoughtworks.springbootemployee.Exception.NoSuchCompanyException;
import com.thoughtworks.springbootemployee.Exception.NoSuchEmployeeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoSuchEmployeeException.class, NoSuchCompanyException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleNoSuchDataException(Exception e) {
        return e.getMessage();
    }


    @ExceptionHandler({IllegalUpdateEmployeeException.class, IllegalUpdateCompanyException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String handleIllegalOperationException(Exception e) {
        return e.getMessage();
    }

}
