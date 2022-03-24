package com.alkemy.ong.infrastructure.spring.config.security.filer;

import com.alkemy.ong.infrastructure.util.JwtUtils;
import com.alkemy.ong.infrastructure.util.ResponseUtils;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private static final String CREDENTIALS = "";

  @Autowired
  private JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (jwtUtils.isTokenSet(authorizationHeader)) {
      try {
        setAuthentication(authorizationHeader);
        filterChain.doFilter(request, response);
      } catch (JwtException e) {
        ResponseUtils.setCustomForbiddenResponse(response);
      }
    } else {
      SecurityContextHolder.clearContext();
      filterChain.doFilter(request, response);
    }
  }

  private void setAuthentication(String authorizationHeader) {
    List<String> authorities = jwtUtils.getAuthorities(authorizationHeader);
    if (authorities != null) {
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          jwtUtils.extractUsername(authorizationHeader),
          CREDENTIALS,
          getGrantedAuthorities(authorities));

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
  }

  private List<SimpleGrantedAuthority> getGrantedAuthorities(List<String> authorities) {
    return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

}
