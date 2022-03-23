package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.WrongCredentialsException;
import com.alkemy.ong.application.rest.request.AuthenticationRequest;
import com.alkemy.ong.application.rest.response.AuthenticationResponse;
import com.alkemy.ong.application.service.abstraction.IAuthenticationService;
import com.alkemy.ong.infrastructure.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private JwtUtils jwtUtils;

  @Override
  public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

    UserDetails userDetails;
    try {
      Authentication auth = authManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
              authenticationRequest.getPassword()));
      userDetails = (UserDetails) auth.getPrincipal();
    } catch (Exception e) {
      throw new WrongCredentialsException("Wrong email or password.");
    }
    final String jwt = jwtUtils.generateToken(userDetails);
    return new AuthenticationResponse(jwt);

  }
}
