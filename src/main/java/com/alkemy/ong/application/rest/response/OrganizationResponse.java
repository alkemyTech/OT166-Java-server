package com.alkemy.ong.application.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationResponse {

  private String name;
  private String image;
  private String address;
  private String phone;
  private List<SlideResponse> slides;
  private SocialMediaResponse socialMedia;
  private String email;
  private String welcomeText;
  private String aboutUsText;

}
