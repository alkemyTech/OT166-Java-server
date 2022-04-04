package com.alkemy.ong.application.rest.request;

import com.alkemy.ong.application.util.RegExpressionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class RegisterRequest {

  @NotNull
  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "Name can contain letters and spaces")
  @Schema(example = "Richard")
  private String firstName;

  @NotNull
  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "Last name can contain letters and spaces")
  @Schema(example = "Fort")
  private String lastName;

  @NotNull
  @Email(message = "The email has invalid format.")
  @Schema(example = "example@email.com")
  private String email;

  @NotNull
  @Length(min = 6, max = 8, message = "The password must be between 6 and 8 characters.")
  @Schema(minLength = 6, maxLength = 8, example = "comander")
  private String password;
}
