package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.UpdateOrganizationRequest;
import com.alkemy.ong.application.rest.response.OrganizationResponse;

public interface IUpdateOrganizationService {

  OrganizationResponse update(UpdateOrganizationRequest updateOrganizationRequest);

}
