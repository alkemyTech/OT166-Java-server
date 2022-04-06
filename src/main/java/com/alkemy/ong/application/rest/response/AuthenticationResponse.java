package com.alkemy.ong.application.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {

  @Schema(example = "Bearer QYNbKMc...")
  private String token;

}
