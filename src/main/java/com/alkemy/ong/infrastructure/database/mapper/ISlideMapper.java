package com.alkemy.ong.infrastructure.database.mapper;

import com.alkemy.ong.application.rest.response.SlideResponse;
import com.alkemy.ong.infrastructure.database.entity.SlideEntity;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ISlideMapper {

  @IterableMapping(qualifiedByName = "slidesWithoutId")
  List<SlideResponse> toListSlideResponse(List<SlideEntity> listSlideEntities);

  @Named("slidesWithoutId")
  @Mapping(target = "id", ignore = true)
  SlideResponse slidesWithoutId(SlideEntity slideEntity);

  SlideResponse toSlideResponse(SlideEntity slideEntity);

  @Named("slidesWithoutIdAndText")
  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "text", ignore = true),
  })
  SlideResponse slidesWithoutIdAndText(SlideEntity slideEntity);

  @IterableMapping(qualifiedByName = "slidesWithoutIdAndText")
  List<SlideResponse> toSlideImageAndOrderResponse(List<SlideEntity> slideEntities);

}
