package com.alkemy.ong.application.rest.request;

import com.alkemy.ong.application.util.RegExpressionUtils;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UpdateUserRequest {

  @Nullable
  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "Name can contain letters and spaces")
  private String firstName;

  @Nullable
  @Pattern(regexp = RegExpressionUtils.ALPHABETIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "Last name can contain letters and spaces")
  private String lastName;

  @Nullable
  @Length(min = 6, max = 8, message = "The password must be between 6 and 8 characters.")
  private String password;

  @Nullable
  private String photo;

}
