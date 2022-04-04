package com.alkemy.ong.application.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

  @Schema(example = "400")
  private int statusCode;
  @Schema(example = "Something went wrong.")
  private String message;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @Schema(example = "Errors.")
  private List<String> moreInfo;

}
