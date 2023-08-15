package com.sb.jvs.exception;

import com.networknt.schema.ValidationMessage;
import com.sb.jvs.model.ValidationFailedMessage;

import java.util.Set;

public class ValidationFailedException extends RuntimeException {
    
    private final ValidationFailedMessage validationFailedMessage;
    public ValidationFailedException(String message, Set<ValidationMessage> errors) {
        super(message);
        this.validationFailedMessage = ValidationFailedMessage.msg(message, errors.stream().map(ValidationMessage::getMessage).toList());
    }
    
    public ValidationFailedException(String message) {
        super(message);
        this.validationFailedMessage = ValidationFailedMessage.builder().message(message).build();
    }
    
    public ValidationFailedException(Throwable throwable) {
        super(throwable);
        this.validationFailedMessage = ValidationFailedMessage.builder().message(throwable.getMessage()).build();
    }
    
    public ValidationFailedMessage getValidationFailedMessage() {
        return this.validationFailedMessage;
    }
}
