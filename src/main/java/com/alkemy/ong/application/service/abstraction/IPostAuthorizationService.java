package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.AuthorizationRequest;
import com.alkemy.ong.application.rest.response.AuthorizationResponse;

public interface IPostAuthorizationService {

  AuthorizationResponse login(AuthorizationRequest authorizationRequest);

}
