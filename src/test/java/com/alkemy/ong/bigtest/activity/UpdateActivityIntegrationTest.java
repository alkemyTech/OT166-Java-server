package com.alkemy.ong.bigtest.activity;


import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.CreateActivityRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.ActivityEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class UpdateActivityIntegrationTest extends BigTest {

  @Test
  public void shouldUpdateActivityWhenRequestUserHasAdminRole() throws Exception {
    ActivityEntity randomActivity = getRandomActivity();
    Long randomActivityId = randomActivity.getId();

    mockMvc.perform(put("/activities/{id}", String.valueOf(randomActivityId))
                .content(getContent("New name", "", "https://s3.com/activity.jpg"))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("New name")))
        .andExpect(jsonPath("$.content", equalTo("")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/activity.jpg")))
        .andExpect(status().isOk());

    assertActivityHasBeenCreated(randomActivityId);
    cleanActivityData(randomActivity);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    ActivityEntity randomActivity = getRandomActivity();

    mockMvc.perform(put("/activities/{id}", String.valueOf(randomActivity.getId()))
            .content(getContent("New name", "", "https://s3.com/activity.jpg"))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());

    cleanActivityData(randomActivity);
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenActivityNotExist() throws Exception {
    String nonExistActivityId = "1000000";

    mockMvc.perform(put("/activities/{id}", nonExistActivityId)
            .content(getContent("New name", "", "https://s3.com/activity.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Activity not found.")))
        .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsTooLong() throws Exception {
    ActivityEntity randomActivity = getRandomActivity();
    String nameTooLong = RandomStringUtils.random(60, ".");

    mockMvc.perform(put("/activities/{id}", String.valueOf(randomActivity.getId()))
            .content(getContent(nameTooLong, "", "https://s3.com/activity.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(2)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The name attribute must not be more than 50 characters")))
        .andExpect(status().isBadRequest());

    cleanActivityData(randomActivity);
  }

  @Test
  public void shouldReturnBadRequestWhenNameContainsNumbers() throws Exception {
    ActivityEntity randomActivity = getRandomActivity();

    mockMvc.perform(put("/activities/{id}", String.valueOf(randomActivity.getId()))
            .content(getContent("Nam3 whit numb3rs","", "https://s3.com/activity.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name has invalid format.")))
        .andExpect(status().isBadRequest());

    cleanActivityData(randomActivity);
  }

  @Test
  public void shouldReturnBadRequestWhenImageContainsBlankSpaces() throws Exception {
    ActivityEntity randomActivity = getRandomActivity();

    mockMvc.perform(put("/activities/{id}", String.valueOf(randomActivity.getId()))
            .content(getContent("","", "https://s3.com/ activity.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The image has invalid format.")))
        .andExpect(status().isBadRequest());

    cleanActivityData(randomActivity);
  }

  private String getContent(String name, String content, String image)
      throws JsonProcessingException {
    return objectMapper.writeValueAsString(CreateActivityRequest.builder()
        .name(name)
        .content(content)
        .image(image)
        .build());
  }

  private void assertActivityHasBeenCreated(Long randomActivityId) {
    Optional<ActivityEntity> optionalActivityEntity = activityRepository.findById(randomActivityId);
    assertTrue(optionalActivityEntity.isPresent());
    assertEquals("New name", optionalActivityEntity.get().getName());
    assertEquals("", optionalActivityEntity.get().getContent());
    assertEquals("https://s3.com/activity.jpg", optionalActivityEntity.get().getImage());
  }

}
