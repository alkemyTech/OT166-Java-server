package com.alkemy.ong.application.rest.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class RegisterRequest {

  @Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ ]+", message = "Name can contain letters and spaces")
  private String firstName;

  @Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ ]+", message = "Last name can contain letters and spaces")
  private String lastName;

  @Email(message = "The email has invalid format.")
  private String email;

  @Length(min = 6, max = 8, message = "The password must be between 6 and 8 characters.")
  private String password;
}
