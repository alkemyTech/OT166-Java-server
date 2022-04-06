package com.alkemy.ong.bigtest.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.AuthenticationRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import org.junit.Test;
import org.springframework.http.MediaType;

public class AuthenticationIntegrationTest extends BigTest {

  @Test
  public void shouldReturnOKStatusCode() throws Exception {

    mockMvc.perform(post("/auth/login")
            .content(objectMapper.writeValueAsString(AuthenticationRequest.builder()
                .email("freedy@krueger.com").password("abcd1234")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldReturnIsUnauthorizedStatusCode() throws Exception {

    mockMvc.perform(post("/auth/login")
            .content(objectMapper.writeValueAsString(AuthenticationRequest.builder()
                .email("freedy@krueger.com").password("badPass")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void shouldReturnBadRequestStatusCode() throws Exception {

    mockMvc.perform(post("/auth/login")
            .content(objectMapper.writeValueAsString(AuthenticationRequest.builder()
                .email("incorrectFormatEmail").password("incorrectFormat")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

}
