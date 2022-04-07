package com.alkemy.ong.application.rest.request;

import static com.alkemy.ong.application.util.RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES;
import static com.alkemy.ong.application.util.RegExpressionUtils.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UpdateMemberRequest {

  @NotNull(message = "The name must not be null")
  @Pattern(regexp = ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The name has invalid format.")
  private String name;
  private String facebookUrl;
  private String instagramUrl;
  private String linkedInUrl;
  @NotNull(message = "The image must not be null")
  @Pattern(regexp = URL, message = "The image has invalid format.")
  private String image;
  private String description;

}
