package com.alkemy.ong.application.rest.request;

import com.alkemy.ong.application.util.RegExpressionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateTestimonialRequest {

  @NotNull(message = "The name must not be null")
  @Size(max = 50, message = "The name attribute must not be more than 50 characters")
  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The name has invalid format.")
  @Schema(example = "Richard Fort")
  String name;

  @Size(max = 150, message = "The name attribute must not be more than 150 characters")
  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The content has invalid format.")
  @Schema(example = "This is a content")
  String content;

  @Schema(example = "https://s3.com/richard.fort.jpg")
  String image;

}
