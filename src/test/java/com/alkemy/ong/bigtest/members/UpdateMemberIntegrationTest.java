package com.alkemy.ong.bigtest.members;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.CreateMemberRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.MemberEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class UpdateMemberIntegrationTest extends BigTest {

  final static String newName = "Magali Kain Senior Developer Consultant";
  final static String newImage = "https://s3.com/maga-smiling.png";

  @Test
  public void shouldUpdateMemberWhenUserHasStandardUserRole() throws Exception {
    final MemberEntity randomMember = getRandomMember();
    mockMvc.perform(put("/members/{id}", String.valueOf(randomMember.getId()))
            .content(getContent(newName, newImage))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.id").value(randomMember.getId()))
        .andExpect(jsonPath("$.name", equalTo(newName)))
        .andExpect(jsonPath("$.image", equalTo(newImage)))
        .andExpect(status().isOk());
    assertMemberHasBeenUpdated(randomMember.getId());
    cleanMemberData(randomMember);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenUserHasAdminRole() throws Exception {
    final MemberEntity randomMember = getRandomMember();
    mockMvc.perform(put("/members/{id}", String.valueOf(randomMember.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
    assertMemberHasNotBeenUpdated(randomMember.getId());
    cleanMemberData(randomMember);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    final MemberEntity randomMember = getRandomMember();
    mockMvc.perform(put("/members/{id}", String.valueOf(randomMember.getId()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
    assertMemberHasNotBeenUpdated(randomMember.getId());
    cleanMemberData(randomMember);
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsNull() throws Exception {
    final MemberEntity randomMember = getRandomMember();
    mockMvc.perform(put("/members/{id}", String.valueOf(randomMember.getId()))
            .content(getContent(null, newImage))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name must not be null")))
        .andExpect(status().isBadRequest());
    assertMemberHasNotBeenUpdated(randomMember.getId());
    cleanMemberData(randomMember);
  }

  @Test
  public void shouldReturnBadRequestWhenInvalidName() throws Exception {
    final String invalidName = "M4gálï K@1ñ";
    final MemberEntity randomMember = getRandomMember();
    mockMvc.perform(put("/members/{id}", String.valueOf(randomMember.getId()))
            .content(getContent(invalidName, newImage))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name has invalid format.")))
        .andExpect(status().isBadRequest());
    assertMemberHasNotBeenUpdated(randomMember.getId());
    cleanMemberData(randomMember);
  }

  @Test
  public void shouldReturnBadRequestWhenImageIsNull() throws Exception {
    final MemberEntity randomMember = getRandomMember();
    mockMvc.perform(put("/members/{id}", String.valueOf(randomMember.getId()))
            .content(getContent(newName, null))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The image must not be null")))
        .andExpect(status().isBadRequest());
    assertMemberHasNotBeenUpdated(randomMember.getId());
    cleanMemberData(randomMember);
  }

  @Test
  public void shouldReturnBadRequestWhenInvalidImage() throws Exception {
    final String invalidImage = "Magali's-dog-photo.png";
    final MemberEntity randomMember = getRandomMember();
    mockMvc.perform(put("/members/{id}", String.valueOf(randomMember.getId()))
            .content(getContent(newName, invalidImage))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The image has invalid format.")))
        .andExpect(status().isBadRequest());
    assertMemberHasNotBeenUpdated(randomMember.getId());
    cleanMemberData(randomMember);
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenUserNotExist() throws Exception {
    final String nonExistMemberId = "1000000";
    mockMvc.perform(put("/members/{id}", nonExistMemberId)
            .content(getContent(newName, newImage))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Member not found.")))
        .andExpect(status().isNotFound());
  }

  private String getContent(String name, String image) throws JsonProcessingException {
    return objectMapper.writeValueAsString(CreateMemberRequest.builder()
        .name(name)
        .image(image)
        .build());
  }

  private void assertMemberHasBeenUpdated(Long memberId) {
    Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
    assertTrue(optionalMemberEntity.isPresent());
    optionalMemberEntity.ifPresent(memberEntity -> {
      assertEquals(newName, memberEntity.getName());
      assertEquals(newImage, memberEntity.getImage());
    });
  }

  private void assertMemberHasNotBeenUpdated(Long memberId) {
    Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
    assertTrue(optionalMemberEntity.isPresent());
    optionalMemberEntity.ifPresent(memberEntity -> {
      assertNotEquals(newName, memberEntity.getName());
      assertNotEquals(newImage, memberEntity.getImage());
    });
  }

}
