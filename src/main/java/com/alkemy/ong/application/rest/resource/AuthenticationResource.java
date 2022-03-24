package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.AuthenticationRequest;
import com.alkemy.ong.application.rest.response.AuthenticationResponse;
import com.alkemy.ong.application.service.abstraction.IAuthenticationService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationResource {

  @Autowired
  private IAuthenticationService authService;

  @PostMapping(path = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthenticationResponse> login(
      @Valid @RequestBody AuthenticationRequest authenticationRequest) {
    return ResponseEntity.ok().body(authService.login(authenticationRequest));
  }

}

