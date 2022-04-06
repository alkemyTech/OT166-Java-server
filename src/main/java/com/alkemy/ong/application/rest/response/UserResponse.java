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
public class UserResponse {

  @Schema(example = "1")
  private Long id;
  @Schema(example = "Richard")
  private String firstName;
  @Schema(example = "Fort")
  private String lastName;
  @Schema(example = "example@email.com")
  private String email;
  @Schema(example = "image")
  private String photo;
  @Schema(example = "ROLE_USER")
  private String role;
}
