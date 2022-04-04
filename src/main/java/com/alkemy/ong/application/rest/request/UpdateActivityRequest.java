package com.alkemy.ong.application.rest.request;

import com.alkemy.ong.application.util.RegExpressionUtils;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UpdateActivityRequest {

  @Size(max = 50,
      message = "The name attribute must not be more than 50 characters")
  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The name has invalid format.")
  private String name;

  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The name has invalid format.")
  private String content;

  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITHOUT_BLANK_SPACES,
      message = "The image has invalid format.")
  private String image;
}
