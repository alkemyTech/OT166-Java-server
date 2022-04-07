package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.UpdateCategoryRequest;
import com.alkemy.ong.application.rest.response.CategoryResponse;

public interface IUpdateCategoryService {

  CategoryResponse update(Long id, UpdateCategoryRequest updateCategoryRequest);

}
