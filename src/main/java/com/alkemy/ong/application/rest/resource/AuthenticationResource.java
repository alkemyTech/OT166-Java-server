package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.AuthenticationRequest;
import com.alkemy.ong.application.rest.request.RegisterRequest;
import com.alkemy.ong.application.rest.response.AuthenticationResponse;
import com.alkemy.ong.application.rest.response.RegisterResponse;
import com.alkemy.ong.application.service.abstraction.IAuthenticationService;
import com.alkemy.ong.application.service.abstraction.IRegisterService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @Autowired
  private IRegisterService registerService;

  @PostMapping(path = "/register",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RegisterResponse> register(
      @Valid @RequestBody RegisterRequest registerRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(registerService.register(registerRequest));
  }

  @PostMapping(path = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthenticationResponse> login(
      @Valid @RequestBody AuthenticationRequest authenticationRequest) {
    return ResponseEntity.ok().body(authService.login(authenticationRequest));
  }

}

