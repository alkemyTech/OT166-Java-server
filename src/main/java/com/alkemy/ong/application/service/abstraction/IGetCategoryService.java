package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.CategoryResponse;

public interface IGetCategoryService {

  CategoryResponse getBy(Long id);

}
