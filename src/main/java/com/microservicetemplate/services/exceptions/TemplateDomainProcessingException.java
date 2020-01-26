package com.microservicetemplate.services.exceptions;

public class TemplateDomainProcessingException extends RuntimeException {
    public TemplateDomainProcessingException(String message) {
        super(message);
    }
}
