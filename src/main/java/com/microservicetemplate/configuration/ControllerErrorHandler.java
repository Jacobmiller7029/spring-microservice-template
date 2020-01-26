package com.microservicetemplate.configuration;

import com.microservicetemplate.services.exceptions.TemplateDomainProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TemplateDomainProcessingException.class)
    private ResponseEntity badRequestHandler(TemplateDomainProcessingException tdpe) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(tdpe.getMessage(), status);
    }

}
