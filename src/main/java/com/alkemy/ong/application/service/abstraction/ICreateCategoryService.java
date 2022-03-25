package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.CategoryRequest;
import com.alkemy.ong.application.rest.response.CategoryResponse;

public interface ICreateCategoryService {

  CategoryResponse createCategory(CategoryRequest categoryRequest);
}
