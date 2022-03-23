package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.AuthorizationRequest;
import com.alkemy.ong.application.rest.response.AuthorizationResponse;
import com.alkemy.ong.application.service.abstraction.IPostAuthorizationService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthorizationResource {

  @Autowired
  private IPostAuthorizationService authService;

  /**
   * This endpoint allows the user to log in by entering their email and password to authenticate.
   * Email and password are validated.
   *
   * @param authorizationRequest The authentication request containing the user's email and
   *                             password
   * @return The authentication response containing the user's jwt token
   */
  @PostMapping(path = "/login")
  public ResponseEntity<AuthorizationResponse> login(
      @Valid @RequestBody AuthorizationRequest authorizationRequest) {
    return ResponseEntity.accepted().body(authService.login(authorizationRequest));
  }

}

