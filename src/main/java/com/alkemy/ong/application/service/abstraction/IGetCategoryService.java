package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.CategoryResponse;
import com.alkemy.ong.application.rest.response.ListCategoriesResponse;
import org.springframework.data.domain.Pageable;

public interface IGetCategoryService {

  CategoryResponse getBy(Long id);

  ListCategoriesResponse listActiveCategories(Pageable pageable);

}
