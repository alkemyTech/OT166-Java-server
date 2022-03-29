package com.alkemy.ong.application.util;

import com.alkemy.ong.infrastructure.spring.config.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

  @Autowired
  private UserDetailsService userDetailsService;

  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public UserDetails getUserAuthenticated() {
    return userDetailsService.loadUserByUsername(this.getAuthentication().getName());
  }

  public boolean hasAdminRole() {
    return getAuthentication().getAuthorities().stream()
        .anyMatch(grantedAuthority -> Role.ADMIN.getFullRoleName()
            .equals(grantedAuthority.getAuthority()));
  }

}
