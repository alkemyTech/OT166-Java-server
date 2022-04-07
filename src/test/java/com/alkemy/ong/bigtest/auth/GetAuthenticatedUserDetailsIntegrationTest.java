package com.alkemy.ong.bigtest.auth;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


public class GetAuthenticatedUserDetailsIntegrationTest extends BigTest {

  @Test
  public void shouldReturnUserWhenHasUserRole() throws Exception {

    mockMvc.perform(get("/auth/me")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.firstName", equalTo("Freddy")))
        .andExpect(jsonPath("$.lastName", equalTo("Krueger")))
        .andExpect(jsonPath("$.email", equalTo("freedy@krueger.com")))
        .andExpect(jsonPath("$.role", equalTo("ROLE_USER")))
        .andExpect(status().isOk());
  }


  @Test
  public void shouldReturnUserWhenHasAdminRole() throws Exception {

    mockMvc.perform(get("/auth/me")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.firstName", equalTo("Jason")))
        .andExpect(jsonPath("$.lastName", equalTo("Voorhees")))
        .andExpect(jsonPath("$.email", equalTo("jason@voorhees.com")))
        .andExpect(jsonPath("$.role", equalTo("ROLE_ADMIN")))
        .andExpect(status().isOk());

  }


}
