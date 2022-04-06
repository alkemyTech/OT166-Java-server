package com.alkemy.ong.bigtest.auth;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsEqual.equalToObject;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
  public void shouldReturnIsUnauthorizedStatusCodeWhenCredentialsAreInvalid() throws Exception {

    mockMvc.perform(post("/auth/login")
            .content(objectMapper.writeValueAsString(AuthenticationRequest.builder()
                .email("freedy@krueger.com").password("badPass")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void shouldReturnBadRequestStatusCodeWhenCredentialsHaveInvalidFormat() throws Exception {

    mockMvc.perform(post("/auth/login")
            .content(objectMapper.writeValueAsString(AuthenticationRequest.builder()
                .email("incorrectFormatEmail").password("incorrectFormat")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

}
