package com.alkemy.ong.application.rest.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateNewsRequest {

  @Size(max = 50 , message = "Maximum size is 50 characters.")
  @Pattern(regexp = "[a-zA-Z\\s]+", message = "The name accepts only alphabetic characters and blank spaces.")
  @NotEmpty(message = "The name must not be empty.")
  private String name;

  @Pattern(regexp = "[a-zA-Z0-9\\s]+", message = "The text accepts only alphanumeric characters and blank spaces.")
  @NotEmpty(message = "The text must not be empty.")
  private String text;

  @Pattern(regexp = "[0-9]+", message = "The image accepts only alphanumeric characters.")
  @NotEmpty(message = "The image must not be empty.")
  private String image;

}
