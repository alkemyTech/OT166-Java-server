package com.alkemy.ong.application.rest.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSlideRequest {

  @NotNull(message = "The text must not be null")
  private String text;
  @NotNull(message = "The order must not be null or must be zero")
  private Integer order;

}
