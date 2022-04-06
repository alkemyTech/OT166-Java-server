package com.alkemy.ong.bigtest.user;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.UpdateUserRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import java.util.Optional;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class UpdateUserIntegrationTest extends BigTest {

  @Test
  public void shouldUpdateUserWhenUserHasAdminRole() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(patch("/users/{id}", String.valueOf(randomUser.getId()))
            .content(objectMapper.writeValueAsString(UpdateUserRequest.builder()
                .lastName("Gordon")
                .build()))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(status().isNoContent());

    Optional<UserEntity> updatedUser = userRepository.findById(randomUser.getId());
    assertTrue(updatedUser.isPresent());
    assertEquals("Gordon", updatedUser.get().getLastName());

    cleanUsersData(randomUser);
  }

  @Test
  public void shouldUpdateUserWhenUserHasStandardUserRole() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(patch("/users/{id}", String.valueOf(randomUser.getId()))
            .content(objectMapper.writeValueAsString(UpdateUserRequest.builder()
                .lastName("Gordon")
                .build()))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(status().isNoContent());

    Optional<UserEntity> updatedUser = userRepository.findById(randomUser.getId());
    assertTrue(updatedUser.isPresent());
    assertEquals("Gordon", updatedUser.get().getLastName());

    cleanUsersData(randomUser);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(patch("/users/{id}", String.valueOf(randomUser.getId()))
            .content(objectMapper.writeValueAsString(UpdateUserRequest.builder()
                .lastName("Gordon")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());

    cleanUsersData(randomUser);
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenUserIsNotFound() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(patch("/users/{id}", "1000000")
            .content(objectMapper.writeValueAsString(UpdateUserRequest.builder()
                .lastName("Gordon")
                .build()))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("User not found.")))
        .andExpect(status().isNotFound());

    cleanUsersData(randomUser);
  }

  @Test
  public void shouldReturnBadRequestWhenPasswordIsTooLong() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(patch("/users/{id}", String.valueOf(randomUser.getId()))
            .content(objectMapper.writeValueAsString(UpdateUserRequest.builder()
                .password("0123456789")
                .build()))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The password must be between 6 and 8 characters.")))
        .andExpect(status().isBadRequest());

    cleanUsersData(randomUser);
  }

  @Test
  public void shouldReturnBadRequestWhenPasswordIsTooShort() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(patch("/users/{id}", String.valueOf(randomUser.getId()))
            .content(objectMapper.writeValueAsString(UpdateUserRequest.builder()
                .password("0123")
                .build()))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The password must be between 6 and 8 characters.")))
        .andExpect(status().isBadRequest());

    cleanUsersData(randomUser);
  }

  @Test
  public void shouldReturnBadRequestWhenFirstNameContainsNumbers() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(patch("/users/{id}", String.valueOf(randomUser.getId()))
            .content(objectMapper.writeValueAsString(UpdateUserRequest.builder()
                .firstName("G0rd0n")
                .build()))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("Name can contain letters and spaces")))
        .andExpect(status().isBadRequest());

    cleanUsersData(randomUser);
  }

  @Test
  public void shouldReturnBadRequestWhenLastNameContainsNumbers() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(patch("/users/{id}", String.valueOf(randomUser.getId()))
            .content(objectMapper.writeValueAsString(UpdateUserRequest.builder()
                .lastName("Fr33m4n")
                .build()))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("Last name can contain letters and spaces")))
        .andExpect(status().isBadRequest());

    cleanUsersData(randomUser);
  }

}
