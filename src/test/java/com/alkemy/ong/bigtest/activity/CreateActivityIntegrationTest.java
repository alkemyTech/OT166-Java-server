package com.alkemy.ong.bigtest.activity;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.CreateActivityRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.ActivityEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class CreateActivityIntegrationTest extends BigTest {

  @Test
  public void shouldCreateActivityWhenRequestUserHasAdminRole() throws Exception {
    String response = mockMvc.perform(post("/activities")
            .content(getContent("Activity Name", "Activity Content", "https://s3.com/activity.jpg"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("Activity Name")))
        .andExpect(jsonPath("$.content", equalTo("Activity Content")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/activity.jpg")))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    Long activityId = JsonPath.read(response, "$.id");
    assertActivityHasBeenCreated(activityId);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(post("/activities")
            .content(getContent("Activity Name", "Activity Content", "https://s3.com/activity.jpg"))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsNull() throws Exception {
    mockMvc.perform(post("/activities")
            .content(getContent(null, "Activity Content", "https://s3.com/activity.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The name must not be null")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenContentIsNull() throws Exception {
    mockMvc.perform(post("/activities")
            .content(getContent("Activity name", null, "https://s3.com/activity.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The content must not be null")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenImageIsNull() throws Exception {
    mockMvc.perform(post("/activities")
            .content(getContent("Activity name", "Activity Content", null))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The image must not be null")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsTooLong() throws Exception {
    String nameTooLong = RandomStringUtils.random(60, ".");

    mockMvc.perform(post("/activities")
            .content(getContent(nameTooLong, "Activity Content", "https://s3.com/activity.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(2)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The name attribute must not be more than 50 characters")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIContainsNumbers() throws Exception {
    mockMvc.perform(post("/activities")
            .content(getContent("Nam3 whit numb3rs", "", ""))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The name has invalid format.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenImageContainsBlankSpaces() throws Exception {
    mockMvc.perform(post("/activities")
            .content(getContent("", "", "https://s3.com/ activity.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The image has invalid format.")))
        .andExpect(status().isBadRequest());
  }

  private String getContent(String name, String content, String image)
      throws JsonProcessingException {
    return objectMapper.writeValueAsString(CreateActivityRequest.builder()
        .name(name)
        .content(content)
        .image(image)
        .build());
  }

  private void assertActivityHasBeenCreated(Long activityId) {
    Optional<ActivityEntity> optionalActivityEntity = activityRepository.findById(activityId);
    assertTrue(optionalActivityEntity.isPresent());
    assertEquals("Activity Name", optionalActivityEntity.get().getName());
    assertEquals("Activity Content", optionalActivityEntity.get().getContent());
    assertEquals("https://s3.com/activity.jpg", optionalActivityEntity.get().getImage());
    cleanActivityData(optionalActivityEntity.get());
  }

}
