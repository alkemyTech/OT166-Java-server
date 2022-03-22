package com.alkemy.ong.application.exception;

import com.alkemy.ong.application.rest.response.ErrorResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

  @ExceptionHandler({EntityNotFound.class})
  protected ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFound e) {
    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        e.getMessage(),
        List.of("Entity not found")
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
