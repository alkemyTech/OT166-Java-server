package com.alkemy.ong.application.exception;

import com.alkemy.ong.application.rest.response.ApiErrorResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

  @ExceptionHandler({EntityNotFound.class})
  protected ResponseEntity<ApiErrorResponse> handleEntityNotFound(EntityNotFound ex) {
    ApiErrorResponse errorResponse = new ApiErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        ex.getMessage(),
        List.of("Entity not found")
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}
