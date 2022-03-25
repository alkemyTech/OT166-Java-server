package com.alkemy.ong.application.rest.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateMemberRequest {

  @NotNull(message = "The name must not be null")
  @Pattern(regexp = "^[a-zA-Z_ ]*$", message = "The name has invalid format.")
  private String name;
  private String facebookUrl;
  private String instagramUrl;
  private String linkedInUrl;
  @NotNull(message = "The image must not be null")
  @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "The image has invalid format.")
  private String image;
  private String description;

}
