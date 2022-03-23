package com.alkemy.ong.application.exception;

public class WrongCredentials extends RuntimeException {

  public WrongCredentials(String message) {
    super(message);
  }

}
