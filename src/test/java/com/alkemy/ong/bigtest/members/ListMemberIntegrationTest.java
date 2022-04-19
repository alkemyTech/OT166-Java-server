package com.alkemy.ong.bigtest.members;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.MemberEntity;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ListMemberIntegrationTest extends BigTest {

  @Test
  public void shouldReturnListOfMemberWhenUserHasAdminRole() throws Exception {
    MemberEntity randomMember = getRandomMember();
    mockMvc.perform(get("/members")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.page", equalTo(0)))
        .andExpect(jsonPath("$.totalPages", equalTo(1)))
        .andExpect(jsonPath("$.members[*].id")
            .value(hasItem(randomMember.getId().intValue())))
        .andExpect(jsonPath("$.members[*].name")
            .value(hasItem(randomMember.getName())))
        .andExpect(jsonPath("$.members[*].image")
            .value(hasItem(randomMember.getImage())))
        .andExpect(status().isOk());
    cleanMemberData(randomMember);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenUserHasStandardUserRole() throws Exception {
    mockMvc.perform(get("/members")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(get("/members")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

}
