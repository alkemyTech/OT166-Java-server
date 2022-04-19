package com.alkemy.ong.bigtest.news;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.CreateNewsRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class UpdateNewsIntegrationTest extends BigTest {

  @Test
  public void shouldUpdateNewsWhenRequestUserHasAdminRole() throws Exception {
    Long newsId = saveNews().getId();

    mockMvc.perform(put("/news/{id}", String.valueOf(newsId))
            .content(getContent("New name", "", "https://s3.com/news.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("New name")))
        .andExpect(jsonPath("$.text", equalTo("")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/news.jpg")))
        .andExpect(status().isOk());

    assertNewsHasBeenUpdated(newsId);
    cleanNewsData();
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {

    mockMvc.perform(put("/news/{id}", "12")
            .content(getContent("New name", "", "https://s3.com/news.jpg"))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenNewsNotExist() throws Exception {
    String nonExistentNewsId = "999";

    mockMvc.perform(put("/news/{id}", nonExistentNewsId)
            .content(getContent("New name", "", "https://s3.com/news.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("News not found.")))
        .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsTooLong() throws Exception {
    String nameTooLong = RandomStringUtils.random(60, "n");

    mockMvc.perform(put("/news/{id}", "12")
            .content(getContent(nameTooLong, "", "https://s3.com/news.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("Maximum size for name is 50 characters.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenNameContainsNumbers() throws Exception {

    mockMvc.perform(put("/news/{id}", "12")
            .content(getContent("N4m3 w1th numb3rs", "", "https://s3.com/news.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The name accepts only alphabetic characters and blank spaces.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenTextContainsSpecialCharacters() throws Exception {

    mockMvc.perform(put("/news/{id}", "12")
            .content(getContent("", "T3xt w1th sp$ecial c#ar4c^er", "https://s3.com/news.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The text accepts only alphanumeric characters and blank spaces.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenImageContainsBlankSpaces() throws Exception {

    mockMvc.perform(put("/news/{id}", "12")
            .content(getContent("", "New Text", "https://s3.com /news.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The image accepts only alphanumeric characters.")))
        .andExpect(status().isBadRequest());
  }

  private String getContent(String name, String text, String image) throws JsonProcessingException {
    return objectMapper.writeValueAsString(CreateNewsRequest.builder()
        .name(name)
        .text(text)
        .image(image)
        .build());
  }

  private void assertNewsHasBeenUpdated(Long newsId) {
    Optional<NewsEntity> optionalNewsEntity = newsRepository.findById(newsId);
    assertTrue(optionalNewsEntity.isPresent());
    assertEquals("New name", optionalNewsEntity.get().getName());
    assertEquals("", optionalNewsEntity.get().getContent());
    assertEquals("https://s3.com/news.jpg", optionalNewsEntity.get().getImage());
  }

}
