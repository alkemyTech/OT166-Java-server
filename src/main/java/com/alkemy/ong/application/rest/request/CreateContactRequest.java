package com.alkemy.ong.application.rest.request;

import static com.alkemy.ong.application.util.RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES;

import com.alkemy.ong.application.util.RegExpressionUtils;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateContactRequest {

  @NotNull(message = "The name must not be null")
  @Pattern(regexp = ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The name has invalid format.")
  private String name;

  private String phone;

  @Email(message = "The email has invalid format.")
  @NotEmpty(message = "The email must not be empty.")
  private String email;

  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The message accepts only alphanumeric characters and blank spaces.")
  @NotEmpty(message = "The message must not be empty.")
  @Size(max = 150, message = "The message must be 150 characters or less")
  private String message;

}
