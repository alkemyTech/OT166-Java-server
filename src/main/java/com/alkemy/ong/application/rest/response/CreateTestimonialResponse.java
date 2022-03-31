package com.alkemy.ong.application.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTestimonialResponse {

  private long id;
  private String name;
  private String content;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String image;

}
