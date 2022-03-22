package com.alkemy.ong.application.rest.response;

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
}
