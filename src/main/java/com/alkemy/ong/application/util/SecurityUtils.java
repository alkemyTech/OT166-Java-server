package com.alkemy.ong.application.util;

import com.alkemy.ong.infrastructure.spring.config.security.Role;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

  public GrantedAuthority getGrantedAuthority() {
    List<GrantedAuthority> auth = (List<GrantedAuthority>) getAuthentication().getAuthorities();
    return auth.get(0);
  }

  public boolean hasAdminRole() {
    Authentication auth = this.getAuthentication();
    return Role.ADMIN.getFullRoleName().equals(getGrantedAuthority().getAuthority());

  }

}
