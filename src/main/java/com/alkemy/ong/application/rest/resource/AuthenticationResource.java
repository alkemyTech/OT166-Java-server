package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.AuthenticationRequest;
import com.alkemy.ong.application.rest.request.RegisterRequest;
import com.alkemy.ong.application.rest.response.AuthenticationResponse;
import com.alkemy.ong.application.rest.response.ErrorResponse;
import com.alkemy.ong.application.rest.response.RegisterResponse;
import com.alkemy.ong.application.rest.response.UserResponse;
import com.alkemy.ong.application.service.abstraction.IAuthenticationService;
import com.alkemy.ong.application.service.abstraction.IGetUserService;
import com.alkemy.ong.application.service.abstraction.IRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Register and login users")
@RestController
@RequestMapping(path = "/auth")
public class AuthenticationResource {

  @Autowired
  private IAuthenticationService authService;

  @Autowired
  private IRegisterService registerService;

  @Autowired
  private IGetUserService getUserService;

  @Operation(summary = "Register", description = "Register a new user.", tags = "Post")
  @ApiResponses(value = {
      @ApiResponse(content = @Content(schema = @Schema(implementation = RegisterResponse.class)),
          responseCode = "201", description = "Returns user created."),
      @ApiResponse(responseCode = "400", description = "Invalid input data.", content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)
      ))
  })
  @PostMapping(path = "/register",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RegisterResponse> register(
      @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
          description = "New user to create", content = @Content(schema = @Schema(implementation =
          RegisterRequest.class))) RegisterRequest registerRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(registerService.register(registerRequest));
  }

  @Operation(summary = "Login", description = "Login.", tags = "Post")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Returns JWT.", content = @Content(
          schema = @Schema(implementation = AuthenticationResponse.class))),
      @ApiResponse(responseCode = "401", description = "Invalid email or password.",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })

  @PostMapping(path = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthenticationResponse> login(
      @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
          description = "User credentials", content = @Content(schema = @Schema(implementation =
          AuthenticationRequest.class))) AuthenticationRequest authenticationRequest) {
    return ResponseEntity.ok().body(authService.login(authenticationRequest));
  }

  @Operation(summary = "Get my data user.", description = "Get my data user.", tags = "Get")
  @ApiResponse(responseCode = "200", description = "Returns my data user.", content = @Content(
      schema = @Schema(implementation = UserResponse.class)))
  @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserResponse> getUser() {
    return ResponseEntity.ok().body(getUserService.getUserAuthenticated());
  }
}