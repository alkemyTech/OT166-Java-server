package com.alkemy.ong.application.service;

import com.alkemy.ong.application.rest.request.CategoryRequest;
import com.alkemy.ong.application.rest.response.CategoryResponse;
import com.alkemy.ong.application.service.abstraction.ICreateCategoryService;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.ICategoryMapper;
import com.alkemy.ong.infrastructure.database.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICreateCategoryService {

  @Autowired
  private ICategoryRepository categoryRepository;

  @Autowired
  private ICategoryMapper categoryMapper;

  @Override
  public CategoryResponse createCategory(CategoryRequest categoryRequest) {
    CategoryEntity entity = categoryMapper.toCategoryEntity(categoryRequest);
    entity.setSoftDeleted(false);
    categoryRepository.save(entity);
    return categoryMapper.toCategoryResponse(entity);
  }
}
