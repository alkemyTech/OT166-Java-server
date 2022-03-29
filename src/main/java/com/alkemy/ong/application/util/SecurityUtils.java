package com.alkemy.ong.application.util;

import com.alkemy.ong.infrastructure.spring.config.security.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public String getUserAuthenticated() {
    return this.getAuthentication().getName();
  }

  public boolean hasAdminRole() {
    Authentication auth = this.getAuthentication();
    return auth.getAuthorities().stream()
        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
            .equals(Role.ADMIN.getFullRoleName()));
  }

}
