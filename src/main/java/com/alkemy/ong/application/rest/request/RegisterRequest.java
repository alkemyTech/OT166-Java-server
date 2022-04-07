package com.alkemy.ong.application.rest.request;

import com.alkemy.ong.application.util.RegExpressionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class RegisterRequest {

  @NotNull(message = "The first name must not be null")
  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "First name can contain letters and spaces")
  @Schema(example = "Richard")
  private String firstName;

  @NotNull(message = "The last name must not be null")
  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "Last name can contain letters and spaces")
  @Schema(example = "Fort")
  private String lastName;

  @NotNull(message = "The email must not be null")
  @Email(message = "The email has invalid format.")
  @Schema(example = "example@email.com")
  private String email;

  @NotNull(message = "The password must not be null")
  @Length(min = 6, max = 8, message = "The password must be between 6 and 8 characters.")
  @Schema(minLength = 6, maxLength = 8, example = "foo1234")
  private String password;
}
