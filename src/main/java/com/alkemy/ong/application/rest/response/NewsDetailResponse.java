package com.alkemy.ong.application.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsDetailResponse {

  private Long id;
  private String name;
  private String text;
  private String image;
  private CategoryResponse category;

}