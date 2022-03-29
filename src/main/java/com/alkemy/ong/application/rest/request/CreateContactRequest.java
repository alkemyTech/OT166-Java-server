package com.alkemy.ong.application.rest.request;

import com.alkemy.ong.application.util.RegExpressionUtils;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateContactRequest {

  @NotNull(message = "The name must not be null")
  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The name accepts only alphabetic characters and blank spaces.")
  private String name;

  private String phone;

  @NotNull(message = "The email must not be null")
  @Email(message = "The email has invalid format.")
  private String email;

  @NotNull(message = "The message must not be null")
  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The message accepts only alphabetic characters and blank spaces.")
  @Length(max = 150, message = "The message must be 150 characteres or less")
  private String message;

}
