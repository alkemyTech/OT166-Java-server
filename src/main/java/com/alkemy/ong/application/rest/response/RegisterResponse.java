package com.alkemy.ong.application.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {

  @Schema(example = "23")
  private Long id;
  @Schema(example = "Richard")
  private String firstName;
  @Schema(example = "Fort")
  private String lastName;
  @Schema(example = "example@email.com")
  private String email;
  private String token;

}
