package com.alkemy.ong.application.rest.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSlideRequest {

  @NotNull(message = "Text must not be null.")
  private String text;
  @NotNull(message = "Order must not be null or zero.")
  private Integer order;

}
