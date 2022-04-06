package com.alkemy.ong.application.rest.request;

import com.alkemy.ong.application.util.RegExpressionUtils;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrganizationRequest {

  @Nullable
  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The name has invalid format.")
  private String name;

  @Nullable
  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITHOUT_BLANK_SPACES,
      message = "The image has invalid format.")
  private String image;

  @Nullable
  @Email(message = "The email has invalid format.")
  private String email;

  @Nullable
  @Pattern(regexp = RegExpressionUtils.ALPHANUMERIC_CHARACTERS_WITH_BLANK_SPACES,
      message = "The welcomeText has invalid format")
  @Length(max = 50)
  private String welcomeText;

  @Nullable
  private String phone;

  @Nullable
  private String address;

  @Nullable
  private String aboutUsText;

  @Nullable
  private SocialMediaRequest socialMedia;

}
