package com.alkemy.ong.application.rest.request;

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
  private String email;

  @Length(min = 6, max = 8, message = "The password must be between 6 and 8 characters.")
  private String password;

}
