package com.alkemy.ong.application.rest.response;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberResponse {

  private Long id;
  private String name;
  private String facebookUrl;
  private String instagramUrl;
  private String linkedInUrl;
  private String image;
  private String description;

}
