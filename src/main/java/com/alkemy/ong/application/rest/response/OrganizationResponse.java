package com.alkemy.ong.application.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrganizationResponse {

  private String name;
  private String image;
  private String address;
  private String phone;
  private List<SlideResponse> slides;
  private SocialMedia socialMedia;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String email;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String welcomeText;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String aboutUsText;

}
