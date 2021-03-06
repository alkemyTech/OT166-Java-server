package com.alkemy.ong.infrastructure.database.mapper;

import com.alkemy.ong.application.rest.request.CreateNewsRequest;
import com.alkemy.ong.application.rest.response.NewsResponse;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface INewsMapper {

  @Mappings({@Mapping(target = "content", source = "createNewsRequest.text")})
  NewsEntity toNewsEntity(CreateNewsRequest createNewsRequest);

  @Named("withoutCategory")
  @Mappings({
      @Mapping(target = "text", source = "newsEntity.content"),
      @Mapping(target = "category", ignore = true)
  })
  NewsResponse toNewsResponse(NewsEntity newsEntity);

  List<NewsResponse> toListNewsResponse(List<NewsEntity> newsEntities);

  @Mappings({@Mapping(target = "text", source = "newsEntity.content")})
  NewsResponse toNewsResponseWithCategory(NewsEntity newsEntity);

}
