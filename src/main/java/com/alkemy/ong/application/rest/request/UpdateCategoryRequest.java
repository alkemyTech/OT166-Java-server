package com.alkemy.ong.application.rest.request;

import com.alkemy.ong.application.util.RegExpressionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateCategoryRequest {

  @NotEmpty(message = "The name must not be empty or null.")
  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The name has an invalid format.")
  @Schema(example = "News", required = true)
  private String name;

  @Schema(example = "All the latest news from my organization", required = true)
  private String description;

  @Schema(example = "https://s3.com/mycategory.jpg", required = true)
  private String image;

}
