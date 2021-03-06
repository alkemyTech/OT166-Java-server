package com.alkemy.ong.application.rest.request;

import com.alkemy.ong.application.util.RegExpressionUtils;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UpdateNewsRequest {

  @Size(max = 50, message = "Maximum size for name is 50 characters.")
  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The name accepts only alphabetic characters and blank spaces.")
  private String name;

  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The text accepts only alphanumeric characters and blank spaces.")
  private String text;

  @Pattern(regexp = RegExpressionUtils.URL,
      message = "The image accepts only alphanumeric characters.")
  private String image;

}
