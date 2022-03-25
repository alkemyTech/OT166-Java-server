package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.request.CategoryRequest;
import com.alkemy.ong.application.rest.response.CategoryResponse;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {

  CategoryEntity toCategoryEntity(CategoryRequest categoryRequest);

  CategoryResponse toCategoryResponse(CategoryEntity entity);

}
