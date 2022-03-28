package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.response.OrganizationResponse;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOrganizationMapper {

  OrganizationResponse toOrganizationResponse(OrganizationEntity organizationEntity);

}
