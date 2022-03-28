package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.request.CreateActivityRequest;
import com.alkemy.ong.application.rest.response.ActivityResponse;
import com.alkemy.ong.infrastructure.database.entity.ActivityEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IActivityMapper {

  ActivityEntity toActivityEntity(CreateActivityRequest request);

  ActivityResponse toActivityResponse(ActivityEntity entity);


}
