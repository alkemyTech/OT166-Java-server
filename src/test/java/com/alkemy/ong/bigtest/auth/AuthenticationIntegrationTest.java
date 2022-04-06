package com.alkemy.ong.bigtest.auth;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;
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
        .andExpect(jsonPath("$.token", notNullValue()))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldReturnIsUnauthorizedStatusCodeWhenCredentialsAreInvalid() throws Exception {

    mockMvc.perform(post("/auth/login")
            .content(objectMapper.writeValueAsString(AuthenticationRequest.builder()
                .email("freedy@krueger.com").password("badPass")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(401)))
        .andExpect(jsonPath("$.message", equalTo("Invalid email or password.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The server cannot return a response due to invalid credentials.")))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void shouldReturnBadRequestStatusCodeWhenCredentialsHaveInvalidFormat() throws Exception {

    mockMvc.perform(post("/auth/login")
            .content(objectMapper.writeValueAsString(AuthenticationRequest.builder()
                .email("incorrectFormatEmail").password("incorrectFormat")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(2)))
        .andExpect(jsonPath("$.moreInfo",
            hasItems("The password must be between 6 and 8 characters.",
                "The email has invalid format.")))
        .andExpect(status().isBadRequest());
  }

}
