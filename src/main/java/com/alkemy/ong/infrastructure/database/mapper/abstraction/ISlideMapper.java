package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.response.SlideResponse;
import com.alkemy.ong.infrastructure.database.entity.SlideEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ISlideMapper {

  List<SlideResponse> toListSlideResponse(List<SlideEntity> listSlideEntities);
  
}
