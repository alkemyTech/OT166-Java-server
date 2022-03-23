package com.alkemy.ong.application.exception;

import com.alkemy.ong.application.rest.response.ErrorResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

  @ExceptionHandler(value = WrongCredentials.class)
  protected ResponseEntity<ErrorResponse> handleWrongCredentials(WrongCredentials e) {

    ErrorResponse errorResponse = buildErrorResponse(
        HttpStatus.UNAUTHORIZED,
        e.getMessage(),
        "The server can’t return a response due to invalid credentials.");
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);

  }

}
