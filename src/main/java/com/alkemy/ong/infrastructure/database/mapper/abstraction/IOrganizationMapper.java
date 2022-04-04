package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.response.OrganizationResponse;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IOrganizationMapper {

  @Mappings({
      @Mapping(target = "socialMedia.facebookUrl", source = "organizationEntity.facebookUrl"),
      @Mapping(target = "socialMedia.instagramUrl", source = "organizationEntity.instagramUrl"),
      @Mapping(target = "socialMedia.linkedInUrl", source = "organizationEntity.linkedInUrl"),
      @Mapping(target = "email", ignore = true),
      @Mapping(target = "welcomeText", ignore = true),
      @Mapping(target = "aboutUsText", ignore = true),
  })
  OrganizationResponse toOrganizationResponse(OrganizationEntity organizationEntity);

  @Mappings({
      @Mapping(target = "facebookUrl", source = "socialMedia.facebookUrl"),
      @Mapping(target = "instagramUrl", source = "socialMedia.instagramUrl"),
      @Mapping(target = "linkedInUrl", source = "socialMedia.linkedInUrl")
  })
  OrganizationEntity toOrganizationEntity(OrganizationResponse organizationResponse);

  @Mappings({
      @Mapping(target = "socialMedia.facebookUrl", source = "organizationEntity.facebookUrl"),
      @Mapping(target = "socialMedia.instagramUrl", source = "organizationEntity.instagramUrl"),
      @Mapping(target = "socialMedia.linkedInUrl", source = "organizationEntity.linkedInUrl"),
  })
  OrganizationResponse toOrganizationResponseFull(OrganizationEntity organizationEntity);

}
