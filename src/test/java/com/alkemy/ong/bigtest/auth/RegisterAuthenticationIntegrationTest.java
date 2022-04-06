package com.alkemy.ong.bigtest.auth;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.RegisterRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import org.junit.Test;
import org.springframework.http.MediaType;

public class RegisterAuthenticationIntegrationTest extends BigTest {

  @Test
  public void shouldPostNewUser() throws Exception {

    mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                .firstName("Pepe")
                .lastName("Le Pew")
                .email("pepeLePew@mail.com")
                .password("pass1234")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.firstName", equalTo("Pepe")))
        .andExpect(jsonPath("$.lastName", equalTo("Le Pew")))
        .andExpect(jsonPath("$.email", equalTo("pepeLePew@mail.com")))
        .andExpect(jsonPath("$.token", notNullValue()))
        .andExpect(status().isCreated());


      UserEntity newUser = userRepository.findByEmail("pepeLePew@mail.com");
      assertTrue(newUser != null);
      assertEquals("Pepe", newUser.getFirstName());
      assertEquals("Le Pew", newUser.getLastName());
  }

  @Test
  public void shouldReturnBadRequestWhenFirstNameIsNull() throws Exception {

    mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                .lastName("Le Pew")
                .email("pepeLePew@mail.com")
                .password("pass1234")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("must not be null")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenFirstNameContainsNumbers() throws Exception {

    mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                .firstName("Pepe 100")
                .lastName("Le Pew")
                .email("pepeLePew@mail.com")
                .password("pass1234")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("Name can contain letters and spaces")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenLastNameIsNull() throws Exception {

    mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                .firstName("Pepe")
                .email("pepeLePew@mail.com")
                .password("pass1234")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("must not be null")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenLastNameContainsNumbers() throws Exception {

    mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                .firstName("Pepe")
                .lastName("Le Pew 1")
                .email("pepeLePew@mail.com")
                .password("pass1234")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("Last name can contain letters and spaces")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenEmailIsNull() throws Exception {

    mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                    .firstName("Pepe")
                .lastName("Le Pew")
                .password("pass1234")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("must not be null")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnConflictWhenEmailIsAlreadyExist() throws Exception {

    mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                .firstName("Pepe")
                .lastName("Le Pew")
                .email("freedy@krueger.com")
                .password("pass1234")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(409)))
        .andExpect(jsonPath("$.message", equalTo("Email is already in use.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The server could not complete the user registration because the email address"
                + " entered is already in use.")))
        .andExpect(status().isConflict());
  }

  @Test
  public void shouldReturnBadRequestWhenEmailHaveInvalidFormat() throws Exception {

    mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                .firstName("Pepe")
                .lastName("Le Pew")
                .email("invalidFormat")
                .password("pass1234")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The email has invalid format.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenPasswordIsNull() throws Exception {

    mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                .firstName("Pepe")
                .lastName("Le Pew")
                .email("pepeLePew@mail.com")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("must not be null")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenPasswordIsTooShort() throws Exception {

    mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                .firstName("Pepe")
                .lastName("Le Pew")
                .email("pepeLePew@mail.com")
                .password("pass")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The password must be between 6 and 8 characters.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenPasswordIsTooLong() throws Exception {

    mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                .firstName("Pepe")
                .lastName("Le Pew")
                .email("pepeLePew@mail.com")
                .password("passwordTooLong")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The password must be between 6 and 8 characters.")))
        .andExpect(status().isBadRequest());
  }



}
