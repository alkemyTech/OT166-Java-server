package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.OrganizationResponse;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;

public interface IGetOrganizationDetailsService {

  OrganizationResponse getPublicOrganizationDetails();

  OrganizationEntity findOrganization();

}
