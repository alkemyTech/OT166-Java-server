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

  private static final String NEW_NAME = "Magali Kain Senior Developer Consultant";
  private static final String NEW_IMAGE = "https://s3.com/maga-smiling.png";

  @Test
  public void shouldUpdateMemberWhenUserHasStandardUserRole() throws Exception {
    MemberEntity randomMember = getRandomMember();

    mockMvc.perform(put("/members/{id}", String.valueOf(randomMember.getId()))
            .content(getContent(NEW_NAME, NEW_IMAGE))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.id").value(randomMember.getId()))
        .andExpect(jsonPath("$.name", equalTo(NEW_NAME)))
        .andExpect(jsonPath("$.image", equalTo(NEW_IMAGE)))
        .andExpect(status().isOk());

    assertMemberHasBeenUpdated(randomMember.getId());
    cleanMemberData(randomMember);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenUserHasAdminRole() throws Exception {
    MemberEntity randomMember = getRandomMember();

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
    mockMvc.perform(put("/members/{id}", "1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsNull() throws Exception {
    MemberEntity randomMember = getRandomMember();

    mockMvc.perform(put("/members/{id}", String.valueOf(randomMember.getId()))
            .content(getContent(null, NEW_IMAGE))
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
    String invalidName = "M4gálï K@1ñ";
    MemberEntity randomMember = getRandomMember();

    mockMvc.perform(put("/members/{id}", String.valueOf(randomMember.getId()))
            .content(getContent(invalidName, NEW_IMAGE))
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
    MemberEntity randomMember = getRandomMember();

    mockMvc.perform(put("/members/{id}", String.valueOf(randomMember.getId()))
            .content(getContent(NEW_NAME, null))
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
    String invalidImage = "Magali's-dog-photo.png";
    MemberEntity randomMember = getRandomMember();

    mockMvc.perform(put("/members/{id}", String.valueOf(randomMember.getId()))
            .content(getContent(NEW_NAME, invalidImage))
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
    String nonExistMemberId = "1000000";

    mockMvc.perform(put("/members/{id}", nonExistMemberId)
            .content(getContent(NEW_NAME, NEW_IMAGE))
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
      assertEquals(NEW_NAME, memberEntity.getName());
      assertEquals(NEW_IMAGE, memberEntity.getImage());
    });
  }

  private void assertMemberHasNotBeenUpdated(Long memberId) {
    Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
    assertTrue(optionalMemberEntity.isPresent());
    optionalMemberEntity.ifPresent(memberEntity -> {
      assertNotEquals(NEW_NAME, memberEntity.getName());
      assertNotEquals(NEW_IMAGE, memberEntity.getImage());
    });
  }

}
