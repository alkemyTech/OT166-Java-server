package com.alkemy.ong.application.exception;

public class WrongCredentialsException extends RuntimeException {

  public WrongCredentialsException(String message) {
    super(message);
  }

}
