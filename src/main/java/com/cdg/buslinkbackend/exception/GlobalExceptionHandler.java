package com.cdg.buslinkbackend.exception;

import com.cdg.buslinkbackend.util.response.ErrorDetails;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError
                        .getField().concat(", ")
                        .concat(Objects.requireNonNull(fieldError.getDefaultMessage())))
                .toList();

        List<String> globalErrors = ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(objectError -> objectError
                        .getObjectName().concat(", ")
                        .concat(Objects.requireNonNull(objectError.getDefaultMessage())))
                .toList();

        List<String> errors = new ArrayList<>();
        errors.addAll(globalErrors);
        errors.addAll(fieldErrors);

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Validation Errors",
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            Exception ex,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ErrorDetails errors = new ErrorDetails(
                LocalDateTime.now(),
                "Constraint Violation" ,
                details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDetails errors = new ErrorDetails(
                LocalDateTime.now(),
                "USER NOT FOUND" ,
                details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


}
