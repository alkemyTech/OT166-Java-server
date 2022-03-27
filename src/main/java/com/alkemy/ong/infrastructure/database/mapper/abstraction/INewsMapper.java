package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.request.CreateNewsRequest;
import com.alkemy.ong.application.rest.response.NewsResponse;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface INewsMapper {

  @Mappings({@Mapping(target = "content", source = "newsRequest.text")})
  NewsEntity toNewsEntity(CreateNewsRequest newsRequest);

  @Mappings({@Mapping(target = "text", source = "newsEntity.content")})
  NewsResponse toNewsResponse(NewsEntity newsEntity);

}
