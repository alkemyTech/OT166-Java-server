package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.response.OrganizationResponse;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IOrganizationMapper {

  IOrganizationMapper INSTANCE = Mappers.getMapper(IOrganizationMapper.class);

  OrganizationResponse toOrganizationResponse(OrganizationEntity entity);

}
