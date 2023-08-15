package com.sb.jvs.exceptionadvice;

import com.sb.jvs.exception.ValidationFailedException;
import com.sb.jvs.model.RuntimeMessage;
import com.sb.jvs.model.ValidationFailedMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidationFailedAdvice {
    
    @ExceptionHandler(ValidationFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ValidationFailedMessage> handleValidationException(final ValidationFailedException validationFailedException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationFailedException.getValidationFailedMessage());
    }
    
    
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<RuntimeMessage> handleRuntimeException(final RuntimeException runtimeException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RuntimeMessage.msg(runtimeException.getMessage()));
    }
}