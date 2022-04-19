package com.alkemy.ong.bigtest.members;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.MemberEntity;
import java.util.Optional;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class DeleteMemberIntegrationTest extends BigTest {

  @Test
  public void shouldDeleteMemberWhenUserHasAdminRole() throws Exception {
    MemberEntity randomMember = getRandomMember();
    mockMvc.perform(delete("/members/{id}", String.valueOf(randomMember.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$").doesNotExist())
        .andExpect(status().isNoContent());
    assertMemberHasBeenDeleted(randomMember.getId());
    cleanMemberData(randomMember);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenUserHasStandardUserRole() throws Exception {
    MemberEntity randomMember = getRandomMember();
    mockMvc.perform(delete("/members/{id}", String.valueOf(randomMember.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
    assertMemberHasNotBeenDeleted(randomMember.getId());
    cleanMemberData(randomMember);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    MemberEntity randomMember = getRandomMember();
    mockMvc.perform(delete("/members/{id}", String.valueOf(randomMember.getId()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
    assertMemberHasNotBeenDeleted(randomMember.getId());
    cleanMemberData(randomMember);
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenUserNotExist() throws Exception {
    final String nonExistMemberId = "1000000";
    mockMvc.perform(delete("/members/{id}", nonExistMemberId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Member not found.")))
        .andExpect(status().isNotFound());
  }

  private void assertMemberHasBeenDeleted(Long memberId) {
    Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
    optionalMemberEntity.ifPresent(memberEntity -> assertTrue(memberEntity.getSoftDeleted()));
  }

  private void assertMemberHasNotBeenDeleted(Long memberId) {
    Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
    optionalMemberEntity.ifPresent(memberEntity -> assertFalse(memberEntity.getSoftDeleted()));
  }

}
