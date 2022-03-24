package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.AuthenticationRequest;
import com.alkemy.ong.application.rest.response.AuthenticationResponse;

public interface IAuthenticationService {

  AuthenticationResponse login(AuthenticationRequest authenticationRequest);

}
