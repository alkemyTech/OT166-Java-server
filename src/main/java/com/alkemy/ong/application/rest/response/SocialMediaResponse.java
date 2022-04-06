package com.alkemy.ong.application.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SocialMediaResponse {

  private String facebookUrl;
  private String instagramUrl;
  private String linkedInUrl;

}
