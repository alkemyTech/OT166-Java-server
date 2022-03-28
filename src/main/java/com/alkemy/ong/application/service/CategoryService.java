package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.request.CreateCategoryRequest;
import com.alkemy.ong.application.rest.response.CategoryResponse;
import com.alkemy.ong.application.service.abstraction.ICreateCategoryService;
import com.alkemy.ong.application.service.abstraction.IDeleteCategoryService;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.ICategoryMapper;
import com.alkemy.ong.infrastructure.database.repository.ICategoryRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICreateCategoryService, IDeleteCategoryService {

  @Autowired
  private ICategoryRepository categoryRepository;

  @Autowired
  private ICategoryMapper categoryMapper;

  @Override
  public CategoryResponse create(CreateCategoryRequest createCategoryRequest) {
    CategoryEntity entity = categoryMapper.toCategoryEntity(createCategoryRequest);
    entity.setSoftDeleted(false);
    categoryRepository.save(entity);
    return categoryMapper.toCategoryResponse(entity);
  }

  @Override
  public void delete(Long id) {
    Optional<CategoryEntity> result = categoryRepository.findById(id);
    if (result.isEmpty() || Boolean.TRUE.equals(result.get().getSoftDeleted())) {
      throw new EntityNotFoundException("Category not found.");
    }

    CategoryEntity categoryEntity = result.get();
    categoryEntity.setSoftDeleted(true);
    categoryRepository.save(categoryEntity);
  }
}
