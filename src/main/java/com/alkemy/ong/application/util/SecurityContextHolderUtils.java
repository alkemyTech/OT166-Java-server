package com.alkemy.ong.application.util;

import com.alkemy.ong.application.service.UserService;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.spring.config.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextHolderUtils {

  @Autowired
  private UserService userService;

  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public UserEntity getUserAuthenticated() {
    Authentication auth = this.getAuthentication();
    UserEntity userEntity = userService.getUser(auth.getName());
    return userEntity;
  }

  public boolean hasAdminRole() {
    Authentication auth = this.getAuthentication();
    return auth.getAuthorities().stream()
        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
  }

}
