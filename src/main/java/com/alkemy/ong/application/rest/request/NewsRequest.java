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
public class NewsRequest {

  @Size(max = 50)
  @Pattern(regexp = "[a-zA-Z\\s]+", message = "name must contain letters and blank spaces")
  @NotEmpty(message = "name may not be empty")
  private String name;

  @Pattern(regexp = "[a-zA-Z0-9\\s]+", message = "text must contain letters and numbers")
  @NotEmpty(message = "text may not be empty")
  private String text;

  @Pattern(regexp = "[0-9]+", message = "image must contain numbers")
  @NotEmpty(message = "image may not be empty")
  private String image;

}
