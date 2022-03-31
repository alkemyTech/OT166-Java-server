package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.response.ListUsersResponse;
import com.alkemy.ong.application.rest.request.UpdateUserRequest;
import com.alkemy.ong.application.rest.response.UpdatedUserResponse;
import com.alkemy.ong.application.service.abstraction.IDeleteUserService;
import com.alkemy.ong.application.service.abstraction.IGetUserService;
import com.alkemy.ong.application.service.abstraction.IUpdateUserService;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IUserMapper;
import com.alkemy.ong.infrastructure.database.repository.IUserRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService, IDeleteUserService, IGetUserService,
    IUpdateUserService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private IUserMapper userMapper;


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return getUser(email);
  }

  private UserEntity getUser(String username) {
    UserEntity userEntity = userRepository.findByEmail(username);
    if (userEntity == null) {
      throw new UsernameNotFoundException("User not found.");
    }
    return userEntity;
  }

  @Override
  public void delete(Long id) {
    UserEntity userEntity = findBy(id);
    userEntity.setSoftDeleted(true);
    userRepository.save(userEntity);
  }

  @Override
  public UpdatedUserResponse update(Long id, UpdateUserRequest updateUserRequest) {
    UserEntity userEntity = findBy(id);
    String firstName = updateUserRequest.getFirstName();
    String lastName = updateUserRequest.getLastName();
    String password = updateUserRequest.getPassword();

    if (firstName != null) {
      userEntity.setFirstName(updateUserRequest.getFirstName());
    }
    if (lastName != null) {
      userEntity.setLastName(updateUserRequest.getLastName());
    }
    if (password != null) {
      userEntity.setPassword(new BCryptPasswordEncoder().encode(updateUserRequest.getPassword()));
    }

    return userMapper.toUpdatedUserResponse(userRepository.save(userEntity));
  }

  private UserEntity findBy(Long id) {
    Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
    if (optionalUserEntity.isEmpty()
        || Boolean.TRUE.equals(optionalUserEntity.get().getSoftDeleted())) {
      throw new EntityNotFoundException("User not found.");
    }
    return optionalUserEntity.get();
  }

  @Override
  public ListUsersResponse listActiveUsers() {
    List<UserEntity> listUserEntities = userRepository.findAllActiveUsers();
    ListUsersResponse listUsersResponse = new ListUsersResponse();
    listUsersResponse.setUsers(userMapper.toListUserResponse(listUserEntities));
    return listUsersResponse;
  }
}
