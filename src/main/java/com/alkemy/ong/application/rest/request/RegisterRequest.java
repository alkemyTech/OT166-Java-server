package com.alkemy.ong.application.rest.request;

import com.alkemy.ong.application.util.RegExpressionUtils;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class RegisterRequest {

  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "Name can contain letters and spaces")
  private String firstName;

  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "Last name can contain letters and spaces")
  private String lastName;

  @Email(message = "The email has invalid format.")
  private String email;

  @Length(min = 6, max = 8, message = "The password must be between 6 and 8 characters.")
  private String password;
}
