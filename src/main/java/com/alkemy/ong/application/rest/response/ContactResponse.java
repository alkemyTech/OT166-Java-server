package com.alkemy.ong.application.rest.response;

import com.amazonaws.services.dynamodbv2.xspec.S;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactResponse {

  private Long id;

  private String name;

  private String phone;

  private String message;

}
