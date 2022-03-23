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
public class MemberRequest {

  @NotNull(message = "This field must not be null")
  @Pattern(regexp = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}", message = "The name has invalid format.")
  private String name;
  private String facebookUrl;
  private String instagramUrl;
  private String linkedInUrl;
  @NotNull(message = "This field must not be null")
  @Pattern(regexp = "^[A-Za-z0-9]*", message = "The image has invalid format.")
  private String image;
  private String description;

}
