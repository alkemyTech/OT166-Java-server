package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.request.CreateCategoryRequest;
import com.alkemy.ong.application.rest.response.CategoryResponse;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {

  CategoryEntity toCategoryEntity(CreateCategoryRequest createCategoryRequest);

  CategoryResponse toCategoryResponse(CategoryEntity entity);

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "description", ignore = true),
      @Mapping(target = "image", ignore = true),
  })
  List<CategoryResponse> toCategoryNameResponses(List<CategoryEntity> categoryEntities);

}
