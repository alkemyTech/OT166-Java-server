package com.alkemy.ong.application.rest.request;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

  @NotEmpty
  private String name;

  private String description;

  private String image;

}
