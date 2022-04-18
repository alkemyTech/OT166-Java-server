package com.alkemy.ong.application.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {

  @Schema(example = "1")
  private Long id;

  @Schema(example = "News")
  private String name;

  @Schema(example = "All the latest news from my organization")
  private String description;

  @Schema(example = "https://s3.com/mycategory.jpg")
  private String image;

}
