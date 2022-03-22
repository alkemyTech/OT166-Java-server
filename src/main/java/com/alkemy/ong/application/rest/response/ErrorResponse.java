package com.alkemy.ong.application.rest.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

  private int statusCode;
  private String message;
  private List<String> errors;

}
