package com.alkemy.ong.application.exception;

import com.alkemy.ong.application.rest.response.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler
  protected ResponseEntity<Object> handleEntityNotFound(RuntimeException ex, WebRequest request) {
    ApiErrorResponse errorResponse = new ApiErrorResponse(
        HttpStatus.BAD_REQUEST,
        ex.getMessage(),
        Arrays.asList("Entity not found")
    );
    return handleExceptionInternal(ex, errorResponse, new HttpHeaders(),
        HttpStatus.BAD_REQUEST, request);
  }
}
