package com.oficinadobrito.api.config.errosandexceptions.base;

import com.oficinadobrito.api.config.errosandexceptions.BadRequestException;
import com.oficinadobrito.api.config.errosandexceptions.InternalServerErrorException;
import com.oficinadobrito.api.config.errosandexceptions.ResourceNotFoundException;
import com.oficinadobrito.api.config.errosandexceptions.dtos.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler{

    //exceptions of domain
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseError> handleResourceNotFound(ResourceNotFoundException ex) {
        ResponseError error = new ResponseError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseError> handleBadRequest(BadRequestException ex) {
        ResponseError error = new ResponseError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ResponseError> handleInternalServerError(InternalServerErrorException ex) {
        ResponseError error = new ResponseError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    //exceptions of http request
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("The params '%s' must be of the type '%s'.", ex.getName(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
        ResponseError error = new ResponseError(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        String errorMessage = "Validation failed for fields: " + validationErrors.toString();
        ResponseError error = new ResponseError(errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
