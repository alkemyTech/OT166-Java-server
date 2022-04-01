package com.alkemy.ong.application.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SlideResponse {

  private String imageUrl;
  private String text;
  private Integer order;

}
