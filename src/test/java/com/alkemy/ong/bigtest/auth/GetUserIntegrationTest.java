package com.alkemy.ong.bigtest.auth;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.spring.config.security.Role;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


public class GetUserIntegrationTest extends BigTest {

  @Test
  public void shouldReturnMyUser() throws Exception {

    mockMvc.perform(get("/auth/me")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.firstName", equalTo("Freddy")))
        .andExpect(jsonPath("$.lastName", equalTo("Krueger")))
        .andExpect(jsonPath("$.email", equalTo("freedy@krueger.com")))
        .andExpect(jsonPath("$.role", equalTo("ROLE_USER")))
        .andExpect(status().isOk());

  }

}
