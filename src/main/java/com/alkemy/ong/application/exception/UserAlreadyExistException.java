package com.alkemy.ong.application.exception;

public class UserAlreadyExistException extends RuntimeException {

  public UserAlreadyExistException(String message) {
    super(message);
  }

}
