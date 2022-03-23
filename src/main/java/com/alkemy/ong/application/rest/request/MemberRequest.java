package com.alkemy.ong.application.rest.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberRequest {

  @NotNull
  //Agregar validacion de caracteres alfabeticos con espacios
  private String name;
  private String facebookUrl;
  private String instagramUrl;
  private String linkedInUrl;
  @NotNull
  //validacion caracteres alfanumericos sin espacios
  private String image;
  private String description;

}
