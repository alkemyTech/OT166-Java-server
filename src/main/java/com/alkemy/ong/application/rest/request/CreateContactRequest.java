package com.alkemy.ong.application.rest.request;

import static com.alkemy.ong.application.util.RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES;

import com.alkemy.ong.application.util.RegExpressionUtils;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateContactRequest {

  @NotNull(message = "Name must not be null")
  @Pattern(regexp = ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES, message = "Name has invalid format.")
  private String name;

  private String phone;

  @Email(message = "Email has invalid format.")
  @NotEmpty(message = "Email must not be empty.")
  private String email;

  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "Message accepts only alphanumeric characters and blank spaces.")
  @NotEmpty(message = "Message must not be empty.")
  @Size(max = 150, message = "Message must be 150 characters or less")
  private String message;

}
