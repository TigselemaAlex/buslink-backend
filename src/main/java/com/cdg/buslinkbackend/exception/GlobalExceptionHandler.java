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

/**
 * A global exception handler.
 */

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        /**
         * It takes the exception, the headers, the status, and the request, and returns
         * a response entity
         * with the object
         * 
         * @param ex      The exception that was thrown
         * @param headers The headers of the request.
         * @param status  The HTTP status code to return.
         * @param request The request that triggered the exception
         * @return A ResponseEntity object.
         */
        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                        HttpHeaders headers, HttpStatusCode status,
                        WebRequest request) {
                List<String> fieldErrors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(fieldError -> fieldError.getDefaultMessage())
                                .toList();

                List<String> globalErrors = ex.getBindingResult()
                                .getGlobalErrors()
                                .stream()
                                .map(objectError -> objectError.getDefaultMessage())
                                .toList();

                List<String> errors = new ArrayList<>();
                errors.addAll(globalErrors);
                errors.addAll(fieldErrors);

                ErrorDetails errorDetails = new ErrorDetails(
                                LocalDateTime.now(),
                                "ERROR DE VALIDACIÓN",
                                errors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        }

        /**
         * It takes an exception, and returns a response entity with a status code of
         * 400, and a body of
         * the error details
         * 
         * @param ex      The exception object.
         * @param request The request that triggered the exception
         * @return A ResponseEntity object.
         */
        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<Object> handleConstraintViolationException(
                        Exception ex,
                        WebRequest request) {

                List<String> details = new ArrayList<>();
                details.add(ex.getMessage());

                ErrorDetails errors = new ErrorDetails(
                                LocalDateTime.now(),
                                "VIOLACIÓN DE REFERENCIAS",
                                details);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        /**
         * It takes an exception and a request, and returns a response entity with a
         * body of an error
         * details object
         * 
         * @param ex      The exception object
         * @param request The request that triggered the exception
         * @return A ResponseEntity object.
         */
        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
                List<String> details = new ArrayList<>();
                details.add(ex.getMessage());
                ErrorDetails errors = new ErrorDetails(
                                LocalDateTime.now(),
                                "USUARIO NO ENCONTRADO",
                                details);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        /**
         * It's a function that handles the exception CooperativeNotFoundException, and
         * returns a response
         * with the status BAD_REQUEST and the body of the error
         * 
         * @param ex      The exception object
         * @param request The request that triggered the exception
         * @return A ResponseEntity object.
         */
        @ExceptionHandler(CooperativeNotFoundException.class)
        public ResponseEntity<Object> handleCooperativeNotFoundException(CooperativeNotFoundException ex,
                        WebRequest request) {
                List<String> details = new ArrayList<>();
                details.add(ex.getMessage());
                ErrorDetails errors = new ErrorDetails(
                                LocalDateTime.now(),
                                "COOPERATIVA NO ENCONTRADA",
                                details);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        /**
         * It handles the exception thrown by the MultipartFile.transferTo() method when
         * the file size
         * exceeds the maximum allowed size
         * 
         * @param ex      The exception object
         * @param request The request that triggered the exception
         * @return A ResponseEntity with a body of type ErrorDetails.
         */
        @ExceptionHandler(SizeLimitExceededException.class)
        public ResponseEntity<Object> handleSizeLimitExceededException(SizeLimitExceededException ex,
                        WebRequest request) {
                List<String> details = new ArrayList<>();
                details.add(ex.getMessage());
                ErrorDetails errors = new ErrorDetails(
                                LocalDateTime.now(),
                                "TAMAÑO MÁXIMO EXCEDIDO",
                                details);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        /**
         * The function is called when the exception RoleNotFoundException is thrown. It
         * returns a
         * ResponseEntity with a status of BAD_REQUEST and a body of type ErrorDetails
         * 
         * @param ex      The exception object
         * @param request The request that caused the exception to be thrown.
         * @return A ResponseEntity object.
         */
        @ExceptionHandler(RoleNotFoundException.class)
        public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException ex, WebRequest request) {
                List<String> details = new ArrayList<>();
                details.add(ex.getMessage());
                ErrorDetails errors = new ErrorDetails(
                                LocalDateTime.now(),
                                "ROL NO ENCONTRADO",
                                details);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        /**
         * It handles the InternalAuthenticationServiceException exception and returns a
         * ResponseEntity
         * with a status of BAD_REQUEST and a body of ErrorDetails
         * 
         * @param ex      The exception object
         * @param request The request that triggered the exception
         * @return A ResponseEntity object.
         */
        @ExceptionHandler(InternalAuthenticationServiceException.class)
        public ResponseEntity<Object> handleInternalAuthenticationServiceException(
                        InternalAuthenticationServiceException ex, WebRequest request) {
                List<String> details = new ArrayList<>();
                details.add(ex.getMessage());
                ErrorDetails errors = new ErrorDetails(
                                LocalDateTime.now(),
                                "CLIENTE NO ENCONTRADO",
                                details);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        /**
         * This function will be called when the application throws a
         * FrequencyNotFoundException
         * 
         * @param ex      The exception object
         * @param request The request that triggered the exception
         * @return A ResponseEntity with a body of type ErrorDetails.
         */
        @ExceptionHandler(FrequencyNotFoundException.class)
        public ResponseEntity<Object> handleFrequencyNotFoundException(FrequencyNotFoundException ex,
                        WebRequest request) {
                List<String> details = new ArrayList<>();
                details.add(ex.getMessage());
                ErrorDetails errors = new ErrorDetails(
                                LocalDateTime.now(),
                                "FRECUENCIA NO ENCONTRADA",
                                details);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        /**
         * The function is called when the exception BusNotFoundException is thrown. It
         * returns a
         * ResponseEntity with the status BAD_REQUEST and the body of the response is an
         * object of type
         * ErrorDetails
         * 
         * @param ex      The exception object
         * @param request The request that triggered the exception
         * @return A ResponseEntity object
         */
        @ExceptionHandler(BusNotFoundException.class)
        public ResponseEntity<Object> handleBusNotFoundException(BusNotFoundException ex, WebRequest request) {
                List<String> details = new ArrayList<>();
                details.add(ex.getMessage());
                ErrorDetails errors = new ErrorDetails(
                                LocalDateTime.now(),
                                "BUS NO ENCONTRADO",
                                details);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        /**
         * The function is called when the TicketNotFoundException is thrown. It returns
         * a ResponseEntity
         * with a status of BAD_REQUEST and a body of the ErrorDetails object
         * 
         * @param ex      The exception object
         * @param request The request that triggered the exception
         * @return A ResponseEntity object.
         */
        @ExceptionHandler(TicketNotFoundException.class)
        public ResponseEntity<Object> handleTicketNotFoundException(TicketNotFoundException ex, WebRequest request) {
                List<String> details = new ArrayList<>();
                details.add(ex.getMessage());
                ErrorDetails errors = new ErrorDetails(
                                LocalDateTime.now(),
                                "TICKET NO ENCONTRADO",
                                details);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

}
