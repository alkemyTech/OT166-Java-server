package com.alkemy.ong.bigtest.news;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.CreateNewsRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class CreateNewsIntegrationTest extends BigTest {

  @Test
  public void shouldCreateNewsWhenRequestUserHasAdminRole() throws Exception {
    CategoryEntity categoryNews = saveCategory("news");

    String response = mockMvc.perform(post("/news")
          .content(getContent("News Name", "News Text", "https://s3.com/news.jpg"))
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("News Name")))
        .andExpect(jsonPath("$.text", equalTo("News Text")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/news.jpg")))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    Integer newsId = JsonPath.read(response, "$.id");
    assertNewsHasBeenCreated(Long.valueOf(newsId));
    cleanCategoryData(categoryNews);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(post("/news")
        .content(getContent("News Name", "News Text", "https://s3.com/news.jpg"))
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsNull() throws Exception {
    mockMvc.perform(post("/news")
          .content(getContent(null, "News Text", "https://s3.com/news.jpg"))
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name must not be empty.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenTextIsNull() throws Exception {
    mockMvc.perform(post("/news")
            .content(getContent("News Name", null, "https://s3.com/news.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The text must not be empty.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenImageIsNull() throws Exception {
    mockMvc.perform(post("/news")
            .content(getContent("News Name", "News Text", null))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The image must not be empty.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsTooLong() throws Exception {
    String nameTooLong = RandomStringUtils.random(60, "n");

    mockMvc.perform(post("/news")
            .content(getContent(nameTooLong, "News Text", "https://s3.com/news.jpg"))
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
    mockMvc.perform(post("/news")
            .content(getContent("N4m3 w1th numb3rs", "News Text", "https://s3.com/news.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The name accepts only alphabetic characters and blank spaces.")))
        .andExpect(status().isBadRequest());
  }

  private String getContent(String name, String text, String image)
      throws JsonProcessingException {
    return objectMapper.writeValueAsString(CreateNewsRequest.builder()
        .name(name)
        .text(text)
        .image(image)
        .build());
  }

  private void assertNewsHasBeenCreated(Long newsId) {
    Optional<NewsEntity> optionalNewsEntity = newsRepository.findById(newsId);
    assertTrue(optionalNewsEntity.isPresent());
    assertEquals("News Name", optionalNewsEntity.get().getName());
    assertEquals("News Text", optionalNewsEntity.get().getContent());
    assertEquals("https://s3.com/news.jpg", optionalNewsEntity.get().getImage());
    cleanNewsData(optionalNewsEntity.get());
  }

}
