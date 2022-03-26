package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.service.abstraction.IDeleteCategoryService;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import com.alkemy.ong.infrastructure.database.repository.ICategoryRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements IDeleteCategoryService {

  @Autowired
  private ICategoryRepository repository;

  @Override
  public void delete(Long id) {
    Optional<CategoryEntity> result = repository.findById(id);
    if (result.isEmpty() || Boolean.TRUE.equals(result.get().getSoftDeleted())) {
      throw new EntityNotFoundException("Category not found.");
    }

    CategoryEntity categoryEntity = result.get();
    categoryEntity.setSoftDeleted(true);
    repository.save(categoryEntity);
  }

}
