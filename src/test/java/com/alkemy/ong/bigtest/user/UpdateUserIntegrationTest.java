package com.alkemy.ong.bigtest.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

}
