package com.alkemy.ong.application.rest.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListCategoriesResponse extends PaginationResponse {

  private List<CategoryResponse> categories;

}
