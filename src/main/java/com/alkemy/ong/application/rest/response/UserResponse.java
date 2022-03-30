package com.alkemy.ong.application.rest.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String photo;
  private String role;
}
