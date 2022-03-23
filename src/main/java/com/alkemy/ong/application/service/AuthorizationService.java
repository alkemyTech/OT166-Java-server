package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.WrongCredentials;
import com.alkemy.ong.application.rest.request.AuthorizationRequest;
import com.alkemy.ong.application.rest.response.AuthorizationResponse;
import com.alkemy.ong.application.service.abstraction.IPostAuthorizationService;
import com.alkemy.ong.infrastructure.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements IPostAuthorizationService {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private JwtUtils jwtUtils;

  @Override
  public AuthorizationResponse login(AuthorizationRequest authorizationRequest) {

    UserDetails userDetails;
    try {
      Authentication auth = authManager.authenticate(
          new UsernamePasswordAuthenticationToken(authorizationRequest.getEmail(),
              authorizationRequest.getPassword()));
      userDetails = (UserDetails) auth.getPrincipal();
    } catch (Exception e) {
      throw new WrongCredentials("Wrong email or password.");
    }
    final String jwt = jwtUtils.generateToken(userDetails);
    return new AuthorizationResponse(jwt);

  }
}
