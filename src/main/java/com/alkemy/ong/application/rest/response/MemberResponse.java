package com.alkemy.ong.application.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberResponse {

  @Schema(example = "23")
  private Long id;

  @Schema(example = "Richard Fort")
  private String name;

  @Schema(example = "https://www.facebook.com/richard.fort")
  private String facebookUrl;

  @Schema(example = "https://www.instagram.com/richard.fort")
  private String instagramUrl;

  @Schema(example = "https://www.linkedin.com/richard.fort")
  private String linkedInUrl;

  @Schema(example = "https://s3.com/richard.fort.jpg")
  private String image;

  @Schema(example = "This is a description")
  private String description;

}
