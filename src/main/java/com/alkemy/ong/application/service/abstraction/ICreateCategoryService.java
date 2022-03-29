package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.CreateCategoryRequest;
import com.alkemy.ong.application.rest.response.CategoryResponse;

public interface ICreateCategoryService {

  CategoryResponse create(CreateCategoryRequest createCategoryRequest);
}
