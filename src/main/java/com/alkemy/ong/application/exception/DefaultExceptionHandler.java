package com.alkemy.ong.application.exception;

import com.alkemy.ong.application.rest.response.ErrorResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

  @ExceptionHandler(value = EntityNotFound.class)
  protected ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFound e) {
    ErrorResponse errorResponse = buildErrorResponse(
        HttpStatus.BAD_REQUEST,
        "Entity not found.",
        e.getMessage());
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

  @ExceptionHandler(value = InvalidCredentialsException.class)
  protected ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
      InvalidCredentialsException e) {
    ErrorResponse errorResponse = buildErrorResponse(
        HttpStatus.UNAUTHORIZED,
        e.getMessage(),
        "The server cannot return a response due to invalid credentials.");
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);

  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    ErrorResponse errorResponse = buildErrorResponse(
        HttpStatus.BAD_REQUEST,
        "Invalid input data.",
        e.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList()));
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  private ErrorResponse buildErrorResponse(HttpStatus httpStatus, String message,
      List<String> moreInfo) {
    return ErrorResponse.builder()
        .statusCode(httpStatus.value())
        .message(message)
        .moreInfo(moreInfo)
        .build();
  }

  private ErrorResponse buildErrorResponse(HttpStatus httpStatus, String message, String moreInfo) {
    return buildErrorResponse(httpStatus, message, List.of(moreInfo));
  }
}
