package com.alkemy.ong.application.rest.request;

import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AuthorizationRequest {

  @Email(message = "This field must be an email")
  private String email;

  @Length(min = 6, max = 8, message = "The password must be between 6 and 8 characters")
  private String password;

}
