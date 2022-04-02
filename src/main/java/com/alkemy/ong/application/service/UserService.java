package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.response.ListUsersResponse;
import com.alkemy.ong.application.rest.response.UserResponse;
import com.alkemy.ong.application.service.abstraction.IDeleteUserService;
import com.alkemy.ong.application.service.abstraction.IGetUserAuthenticateService;
import com.alkemy.ong.application.service.abstraction.IGetUserService;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IUserMapper;
import com.alkemy.ong.infrastructure.database.repository.IUserRepository;
import com.alkemy.ong.infrastructure.util.JwtUtils;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService, IDeleteUserService, IGetUserService, 
    IGetUserAuthenticateService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private IUserMapper userMapper;

  @Autowired
  private JwtUtils jwtUtils;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return getUser(email);
  }

  @Override
  public void delete(Long id) {
    UserEntity userEntity = findBy(id);
    userEntity.setSoftDeleted(true);
    userRepository.save(userEntity);
  }

  @Override
  public ListUsersResponse listActiveUsers() {
    List<UserEntity> listUserEntities = userRepository.findAllActiveUsers();
    ListUsersResponse listUsersResponse = new ListUsersResponse();
    listUsersResponse.setUsers(userMapper.toListUserResponse(listUserEntities));
    return listUsersResponse;
  }

  @Override
  public UserResponse getUserAuthenticated(String authorization) {
    UserEntity userEntity = getUser(jwtUtils.extractUsernameFromHeader(authorization));
    return userMapper.toUserResponse(userEntity);
  }

  private UserEntity getUser(String username) {
    UserEntity userEntity = userRepository.findByEmail(username);
    if (userEntity == null) {
      throw new UsernameNotFoundException("User not found.");
    }
    return userEntity;
  }

  private UserEntity findBy(Long id) {
    Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
    if (optionalUserEntity.isEmpty()
        || Boolean.TRUE.equals(optionalUserEntity.get().getSoftDeleted())) {
      throw new EntityNotFoundException("User not found.");
    }
    return optionalUserEntity.get();
  }
}
