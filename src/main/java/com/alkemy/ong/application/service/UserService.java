package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.response.ListUserResponse;
import com.alkemy.ong.application.rest.response.UserResponse;
import com.alkemy.ong.application.service.abstraction.IDeleteUserService;
import com.alkemy.ong.application.service.abstraction.IGetListUserService;
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
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService, IDeleteUserService, IGetListUserService {

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

  private UserEntity findBy(Long id) {
    Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
    if (optionalUserEntity.isEmpty()
        || Boolean.TRUE.equals(optionalUserEntity.get().getSoftDeleted())) {
      throw new EntityNotFoundException("User not found.");
    }
    return optionalUserEntity.get();
  }

  @Override
  public ListUserResponse getList() {
    List<UserEntity> listUserEntities = userRepository.findAllNotDeleted();
    ListUserResponse listUserResponse = new ListUserResponse();
    listUserResponse.setUsers(userMapper.toListUserResponse(listUserEntities));
    return listUserResponse;
  }
}
