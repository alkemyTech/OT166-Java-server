package com.alkemy.ong.infrastructure.spring.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@EnableWebSecurity
@Configuration
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().anyRequest().permitAll();
    /*http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);*/
    http.exceptionHandling().authenticationEntryPoint(forbiddenEntryPoint());
  }

  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public Http403ForbiddenEntryPoint forbiddenEntryPoint() {
    return new Http403ForbiddenEntryPoint();

  }

}
