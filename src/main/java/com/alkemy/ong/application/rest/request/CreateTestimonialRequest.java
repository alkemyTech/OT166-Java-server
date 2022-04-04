package com.alkemy.ong.application.rest.request;

import com.alkemy.ong.application.util.RegExpressionUtils;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateTestimonialRequest {

  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "Name can only have alphabetic characters with spaces.")
  @Size(max = 50, message = "Name cannot have more than 50 characters.")
  @NotBlank(message = "Name cannot be null or empty.")
  private String name;

  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "Content can only have alphanumeric characters with spaces.")
  @Size(max = 150, message = "Content cannot have more than 150 characters.")
  @NotBlank(message = "Content cannot be null or empty.")
  private String content;

  private String image;

}
