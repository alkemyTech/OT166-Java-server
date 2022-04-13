package com.alkemy.ong.application.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestimonialResponse {

  @Schema(example = "25")
  private long id;

  @Schema(example = "Richard Fort")
  private String name;

  @Schema(example = "This is a content")
  private String content;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(example = "https://s3.com/richard.fort.jpg")
  private String image;

}
