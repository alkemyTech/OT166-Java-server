package com.alkemy.ong.application.exception;

public class EntityNotFound extends RuntimeException {
  public EntityNotFound(String error) {
    super(error);
  }
}
