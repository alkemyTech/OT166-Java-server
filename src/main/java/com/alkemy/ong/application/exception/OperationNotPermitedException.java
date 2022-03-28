package com.alkemy.ong.application.exception;

public class OperationNotPermitedException extends RuntimeException {

  public OperationNotPermitedException(String message) {
    super(message);
  }
}
