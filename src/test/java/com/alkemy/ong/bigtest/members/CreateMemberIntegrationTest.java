package com.alkemy.ong.bigtest.members;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.CreateMemberRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.MemberEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class CreateMemberIntegrationTest extends BigTest {

  private static final String correctName = "Magali Kain";
  private static final String correctImage = "https://s3.com/maga.png";

  @Test
  public void shouldCreateMemberWhenUserHasStandardUserRole() throws Exception {

    String response = mockMvc.perform(post("/members")
            .content(getContent(correctName, correctImage))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("Magali Kain")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/maga.png")))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    Integer memberId = JsonPath.read(response, "$.id");
    assertMemberHasBeenCreated(Long.valueOf(memberId));
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenUserHasAdminRole() throws Exception {
    mockMvc.perform(post("/members")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(post("/members")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsNull() throws Exception {
    mockMvc.perform(post("/members")
            .content(getContent(null, correctImage))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name must not be null")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenInvalidName() throws Exception {
    mockMvc.perform(post("/members")
            .content(getContent("M4gálï K@1ñ", correctImage))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name has invalid format.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenImageIsNull() throws Exception {
    mockMvc.perform(post("/members")
            .content(getContent(correctName, null))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The image must not be null")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenInvalidImage() throws Exception {
    mockMvc.perform(post("/members")
            .content(getContent(correctName, "Magali's-dog-photo.png"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The image has invalid format.")))
        .andExpect(status().isBadRequest());
  }

  private String getContent(String name, String image) throws JsonProcessingException {
    return objectMapper.writeValueAsString(CreateMemberRequest.builder()
        .name(name)
        .image(image)
        .build());
  }

  private void assertMemberHasBeenCreated(Long memberId) {
    Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
    assertTrue(optionalMemberEntity.isPresent());
    optionalMemberEntity.ifPresent(memberEntity -> {
      assertEquals(correctName, memberEntity.getName());
      assertEquals(correctImage, memberEntity.getImage());
      cleanMemberData(memberEntity);
    });
  }

}
