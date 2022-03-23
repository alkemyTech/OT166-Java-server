package com.alkemy.ong.application.exception;

import com.alkemy.ong.application.rest.response.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class DefaultExceptionHandler {

  @ExceptionHandler(value = EntityNotFound.class)
  protected ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFound e) {
    ErrorResponse errorResponse = buildErrorResponse(
        HttpStatus.BAD_REQUEST,
        e.getMessage(),
        "Entity not found.");
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = UsernameNotFoundException.class)
  protected ResponseEntity<ErrorResponse> handleUsernameNotFoundException(
      UsernameNotFoundException e) {
    ErrorResponse errorResponse = buildErrorResponse(
        HttpStatus.NOT_FOUND,
        e.getMessage(),
        "No record with the given email.");
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  private ErrorResponse buildErrorResponse(HttpStatus httpStatus, String message, String moreInfo) {
    return ErrorResponse.builder()
        .statusCode(httpStatus.value())
        .message(message)
        .moreInfo(List.of(moreInfo))
        .build();
  }


  @ExceptionHandler
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {

    List<String> errors = new ArrayList<String>();

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + " -> " + error.getDefaultMessage());
    }

    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + " -> " + error.getDefaultMessage());
    }

    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        ex.getMessage(),
        errors
    );
    return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
  }
}
