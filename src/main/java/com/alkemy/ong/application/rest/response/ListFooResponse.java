package com.alkemy.ong.application.rest.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ListFooResponse {

  private List<FooResponse> fooes;

}
