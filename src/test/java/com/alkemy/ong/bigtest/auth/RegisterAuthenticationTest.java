package com.alkemy.ong.bigtest.auth;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.RegisterRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import org.junit.Test;
import org.springframework.http.MediaType;

public class RegisterAuthenticationTest extends BigTest {

  @Test
  public void registerNewUser() throws Exception {

    mockMvc.perform(patch("/auth/register")
            .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                .firstName("Pepe")
                .lastName("Le Pew")
                .email("pepeLePew@mail.com")
                .password("pass1234")
                .build()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.firstName", equalTo("Pepe")))
        .andExpect(jsonPath("$.lastName", equalTo("Le Pew")))
        .andExpect(jsonPath("$.email", equalTo("pepeLePew@mail.com")))
        .andExpect(status().isOk());

    /*
    try {
      UserEntity newUser = userRepository.findByEmail("pepeLePew@mail.com");
      assertTrue(newUser != null);
      assertEquals("Pepe", newUser.getFirstName());
    } catch (Exception e) {
      throw e;
    }

     */


  }

}
