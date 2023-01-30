package com.cdg.buslinkbackend.exception;

import com.cdg.buslinkbackend.util.response.ErrorDetails;
import jakarta.validation.ConstraintViolationException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage()
                        )
                .toList();

        List<String> globalErrors = ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage()
                        )
                .toList();

        List<String> errors = new ArrayList<>();
        errors.addAll(globalErrors);
        errors.addAll(fieldErrors);

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "ERROR DE VALIDACIÓN",
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            Exception ex,
            WebRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorDetails errors = new ErrorDetails(
                LocalDateTime.now(),
                "VIOLACIÓN DE REFERENCIAS" ,
                details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDetails errors = new ErrorDetails(
                LocalDateTime.now(),
                "USUARIO NO ENCONTRADO" ,
                details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(CooperativeNotFoundException.class)
    public ResponseEntity<Object> handleCooperativeNotFoundException(CooperativeNotFoundException ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDetails errors = new ErrorDetails(
                LocalDateTime.now(),
                "COOPERATIVA NO ENCONTRADA",
                details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<Object> handleSizeLimitExceededException(SizeLimitExceededException ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDetails errors = new ErrorDetails(
                LocalDateTime.now(),
                "TAMAÑO MÁXIMO EXCEDIDO" ,
                details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDetails errors = new ErrorDetails(
                LocalDateTime.now(),
                "ROL NO ENCONTRADO" ,
                details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Object> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDetails errors = new ErrorDetails(
                LocalDateTime.now(),
                "CLIENTE NO ENCONTRADO" ,
                details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(FrequencyNotFoundException.class)
    public ResponseEntity<Object> handleFrequencyNotFoundException(FrequencyNotFoundException ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDetails errors = new ErrorDetails(
                LocalDateTime.now(),
                "FRECUENCIA NO ENCONTRADA" ,
                details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(BusNotFoundException.class)
    public ResponseEntity<Object> handleBusNotFoundException(BusNotFoundException ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDetails errors = new ErrorDetails(
                LocalDateTime.now(),
                "BUS NO ENCONTRADO" ,
                details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<Object> handleTicketNotFoundException(TicketNotFoundException ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDetails errors = new ErrorDetails(
                LocalDateTime.now(),
                "TICKET NO ENCONTRADO" ,
                details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
