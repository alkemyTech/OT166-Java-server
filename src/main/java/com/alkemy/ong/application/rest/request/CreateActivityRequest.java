package com.alkemy.ong.application.rest.request;

import com.alkemy.ong.application.util.RegExpressionUtils;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateActivityRequest {

  @NotNull(message = "The name must not be null")
  @Size(max = 50,
      message = "The name attribute must not be more than 50 characters")
  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The name has invalid format.")
  private String name;

  @NotNull(message = "The content must not be null")
  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The content has invalid format.")
  private String content;

  @NotNull(message = "The image must not be null")
  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITHOUT_BLANK_SPACES,
      message = "The image has invalid format.")
  private String image;

}
