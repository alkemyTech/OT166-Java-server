package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.request.CreateCategoryRequest;
import com.alkemy.ong.application.rest.request.UpdateCategoryRequest;
import com.alkemy.ong.application.rest.response.CategoryResponse;
import com.alkemy.ong.application.service.abstraction.ICreateCategoryService;
import com.alkemy.ong.application.service.abstraction.IDeleteCategoryService;
import com.alkemy.ong.application.service.abstraction.IGetCategoryService;
import com.alkemy.ong.application.service.abstraction.IUpdateCategoryService;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.ICategoryMapper;
import com.alkemy.ong.infrastructure.database.repository.ICategoryRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICreateCategoryService, IDeleteCategoryService,
    IGetCategoryService, IUpdateCategoryService {

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
    CategoryEntity categoryEntity = findBy(id);
    categoryEntity.setSoftDeleted(true);
    categoryRepository.save(categoryEntity);
  }

  @Override
  public CategoryResponse getBy(Long id) {
    return categoryMapper.toCategoryResponse(findBy(id));
  }

  private CategoryEntity findBy(Long id) {
    Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(id);
    if (optionalCategoryEntity.isEmpty()
        || Boolean.TRUE.equals(optionalCategoryEntity.get().getSoftDeleted())) {
      throw new EntityNotFoundException("Category not found.");
    }
    return optionalCategoryEntity.get();
  }


  @Override
  public CategoryResponse update(Long id, UpdateCategoryRequest updateCategoryRequest) {
    CategoryEntity categoryEntity = findBy(id);
    categoryEntity.setName(updateCategoryRequest.getName());
    categoryEntity.setDescription(updateCategoryRequest.getDescription());
    categoryEntity.setImage(updateCategoryRequest.getImage());
    return categoryMapper.toCategoryResponse(categoryRepository.save(categoryEntity));
  }
}
