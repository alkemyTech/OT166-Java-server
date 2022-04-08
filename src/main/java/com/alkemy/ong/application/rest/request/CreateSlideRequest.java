package com.alkemy.ong.application.rest.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSlideRequest {

  @NotBlank(message = "A 64-bit encoded image must be provided")
  private String encodedImage;
  @NotBlank(message = "File type must be provided (ie. jpg, gif, png)")
  private String contentType;
  @NotBlank(message = "The name of the image must be provided")
  private String fileName;
  private Integer order;

}
