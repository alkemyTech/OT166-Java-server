package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.InvalidCredentialsException;
import com.alkemy.ong.application.rest.request.AuthenticationRequest;
import com.alkemy.ong.application.rest.request.RegisterRequest;
import com.alkemy.ong.application.rest.response.AuthenticationResponse;
import com.alkemy.ong.application.rest.response.RegisterResponse;
import com.alkemy.ong.application.service.abstraction.IAuthenticationService;
import com.alkemy.ong.application.service.abstraction.IRegisterService;
import com.alkemy.ong.infrastructure.database.entity.RoleEntity;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IUserMapper;
import com.alkemy.ong.infrastructure.database.repository.IRoleRepository;
import com.alkemy.ong.infrastructure.database.repository.IUserRepository;
import com.alkemy.ong.infrastructure.spring.config.security.Role;
import com.alkemy.ong.infrastructure.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService, IRegisterService {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private IUserMapper userMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private IRoleRepository roleRepository;

  @Override
  public RegisterResponse register(RegisterRequest registerRequest) {
    if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
      throw new InvalidCredentialsException("Email is already in use.");
    }
    RoleEntity userRole = roleRepository.findByName("ROLE_" + Role.USER.name());

    UserEntity newUser = userMapper.toUserEntity(registerRequest);
    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    newUser.setSoftDeleted(false);
    newUser.setRole(userRole);
    newUser = userRepository.save(newUser);

    return userMapper.toRegisterResponse(newUser);
  }

  @Override
  public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
    Authentication authentication;
    try {
      authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
          authenticationRequest.getEmail(),
          authenticationRequest.getPassword()));
    } catch (Exception e) {
      throw new InvalidCredentialsException("Invalid email or password.");
    }

    String jwt = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());
    return new AuthenticationResponse(jwt);
  }

}
