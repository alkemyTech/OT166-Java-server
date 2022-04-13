package com.alkemy.ong.application.rest.request;

import static com.alkemy.ong.application.util.RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES;
import static com.alkemy.ong.application.util.RegExpressionUtils.URL;

import io.swagger.v3.oas.annotations.media.Schema;
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
  @Schema(example = "Richard Fort")
  private String name;

  @Schema(example = "https://www.facebook.com/richard.fort")
  private String facebookUrl;

  @Schema(example = "https://www.instagram.com/richard.fort")
  private String instagramUrl;

  @Schema(example = "https://www.linkedin.com/richard.fort")
  private String linkedInUrl;

  @NotNull(message = "The image must not be null")
  @Pattern(regexp = URL, message = "The image has invalid format.")
  @Schema(example = "https://s3.com/richard.fort.jpg")
  private String image;

  @Schema(example = "This is a description")
  private String description;

}
