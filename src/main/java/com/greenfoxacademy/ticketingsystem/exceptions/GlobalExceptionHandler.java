package com.greenfoxacademy.ticketingsystem.exceptions;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleResourceNotFound(ResourceNotFoundException ex) {
    ExceptionResponse response = ExceptionResponse.builder().message(ex.getMessage()).code(404)
        .timestamp(LocalDateTime.now()).build();

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<ExceptionResponse> handleResourceAlreadyExists(
      ResourceAlreadyExistsException ex) {
    ExceptionResponse response = ExceptionResponse.builder().message(ex.getMessage()).code(409)
        .timestamp(LocalDateTime.now()).build();

    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(BadResourceException.class)
  public ResponseEntity<ExceptionResponse> handleBadResource(BadResourceException ex) {
    ExceptionResponse response = ExceptionResponse.builder().message(ex.getMessage()).code(400)
        .timestamp(LocalDateTime.now()).build();

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    ExceptionResponse response =
        ExceptionResponse.builder().message("Invalid Request").code(400).validationErrors(errors)
            .timestamp(LocalDateTime.now()).build();

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }


}
