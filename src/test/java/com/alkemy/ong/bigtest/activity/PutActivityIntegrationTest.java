package com.alkemy.ong.bigtest.activity;


import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
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
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class PutActivityIntegrationTest extends BigTest {

  @Test
  public void shouldPutActivityWhenRequestUserHasAdminRole() throws Exception {

    ActivityEntity randomActivity = getRandomActivity();
    Long randomActivityId = randomActivity.getId();

    mockMvc.perform(put("/activities/{id}", String.valueOf(randomActivityId))
                .content(getContent("New name", "", ""))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(status().isOk());

    Optional<ActivityEntity> updatedActivity = activityRepository.findById(randomActivityId);
    assertTrue(updatedActivity.isPresent());
    assertEquals("New name", updatedActivity.get().getName());
    assertEquals("", updatedActivity.get().getContent());
    assertEquals("", updatedActivity.get().getImage());

    cleanActivityData(randomActivity);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {

    ActivityEntity randomActivity = getRandomActivity();

    mockMvc.perform(put("/activities/{id}", String.valueOf(randomActivity.getId()))
            .content(getContent("New name", "", ""))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());

    cleanActivityData(randomActivity);
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenActivityNotExist() throws Exception {

    String nonExistUActivityId = "1000000";

    mockMvc.perform(put("/activities/{id}", nonExistUActivityId)
            .content(getContent("New name", "", ""))
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

    mockMvc.perform(put("/activities/{id}", String.valueOf(randomActivity.getId()))
            .content(getContent("NameToLong.........................................",
                "", ""))
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
  public void shouldReturnBadRequestWhenNameIContainsNumbers() throws Exception {

    ActivityEntity randomActivity = getRandomActivity();

    mockMvc.perform(put("/activities/{id}", String.valueOf(randomActivity.getId()))
            .content(getContent("Nam3 whit numb3rs","", ""))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The name has invalid format.")))
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
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The image has invalid format.")))
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

}
