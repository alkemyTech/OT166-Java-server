package com.alkemy.ong.application.rest.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

  private int statusCode;
  private String message;
  private List<String> moreInfo;

}
