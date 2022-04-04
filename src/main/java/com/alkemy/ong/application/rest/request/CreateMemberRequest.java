package com.alkemy.ong.application.rest.request;

import static com.alkemy.ong.application.util.RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES;
import static com.alkemy.ong.application.util.RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITHOUT_BLANK_SPACES;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateMemberRequest {

  @NotNull(message = "The name must not be null")
  @Pattern(regexp = ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The name has invalid format.")
  private String name;
  private String facebookUrl;
  private String instagramUrl;
  private String linkedInUrl;
  @NotNull(message = "The image must not be null")
  @Pattern(regexp = ALPHANUMERIC_CHARACTERS_WITHOUT_BLANK_SPACES,
      message = "The image has invalid format.")
  private String image;
  private String description;

}
