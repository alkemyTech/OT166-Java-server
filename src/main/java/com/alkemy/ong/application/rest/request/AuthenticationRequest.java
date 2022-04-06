package com.alkemy.ong.application.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

  @Email(message = "The email has invalid format.")
  @Schema(example = "example@email.com")
  private String email;

  @Length(min = 6, max = 8, message = "The password must be between 6 and 8 characters.")
  @Schema(minLength = 6, maxLength = 8, example = "comander")
  private String password;

}
