package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.exception.InvalidCredentialsException;
import com.alkemy.ong.application.service.abstraction.IDeleteUserService;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.database.repository.IUserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService, IDeleteUserService {

  @Autowired
  private IUserRepository userRepository;


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
    Optional<UserEntity> userRepo = userRepository.findById(id);
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    String usernameDetails = userDetails.getUsername();
    if (userRepo.isEmpty() || Boolean.TRUE.equals(userRepo.get().getSoftDeleted())) {
      throw new EntityNotFoundException("User not found.");
    }
    if (!usernameDetails.equals(userRepo.get().getUsername())) {
      throw new InvalidCredentialsException("Invalid user.");
    }

    UserEntity userEntity = userRepo.get();
    userEntity.setSoftDeleted(true);
    userRepository.save(userEntity);
  }

}
