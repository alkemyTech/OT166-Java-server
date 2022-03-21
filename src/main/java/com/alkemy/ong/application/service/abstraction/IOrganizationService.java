package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.exception.EntityNotFound;
import com.alkemy.ong.application.rest.response.OrganizationResponse;

public interface IOrganizationService {

  OrganizationResponse getPublicOrganization() throws EntityNotFound;
}
