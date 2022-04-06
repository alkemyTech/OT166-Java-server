package com.alkemy.ong.bigtest.user;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

class DeleteUserIntegrationTest extends BigTest {

  @Test
  public void shouldDeleteUserWhenUserHasAdminRole() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(delete("/users/{id}", String.valueOf(randomUser.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$").doesNotExist())
        .andExpect(status().isNoContent());

    Optional<UserEntity> updatedUser = userRepository.findById(randomUser.getId());
    updatedUser.ifPresent(userEntity -> assertFalse(userEntity.isEnabled()));

    cleanUsersData(randomUser);
  }

  @Test
  public void shouldDeleteUserWhenUserHasStandardUserRole() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(delete("/users/{id}", String.valueOf(randomUser.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$").doesNotExist())
        .andExpect(status().isNoContent());

    Optional<UserEntity> updatedUser = userRepository.findById(randomUser.getId());
    updatedUser.ifPresent(userEntity -> assertFalse(userEntity.isEnabled()));

    cleanUsersData(randomUser);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(delete("/users/{id}", String.valueOf(randomUser.getId()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());

    Optional<UserEntity> updatedUser = userRepository.findById(randomUser.getId());
    updatedUser.ifPresent(userEntity -> assertTrue(userEntity.isEnabled()));

    cleanUsersData(randomUser);
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenUserIsNotFound() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(delete("/users/{id}", "100000")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("User not found.")))
        .andExpect(status().isNotFound());

    Optional<UserEntity> updatedUser = userRepository.findById(randomUser.getId());
    updatedUser.ifPresent(userEntity -> assertTrue(userEntity.isEnabled()));

    cleanUsersData(randomUser);
  }

}