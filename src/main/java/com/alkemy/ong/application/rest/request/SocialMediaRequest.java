package com.alkemy.ong.application.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialMediaRequest {

  @Nullable
  private String facebookUrl;

  @Nullable
  private String instagramUrl;

  @Nullable
  private String linkedInUrl;

}
