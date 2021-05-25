package com.example.schoolapp.controllers.advice;

import com.example.schoolapp.controllers.BaseController;
import com.example.schoolapp.exceptions.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ErrorController extends BaseController {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> resourceNotFoundException(ServiceException e) {
        return buildResponse(buildErrorResponse(e), HttpStatus.NOT_FOUND);
    }

}
