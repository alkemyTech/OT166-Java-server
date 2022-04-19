package com.alkemy.ong.bigtest.news;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class GetNewsIntegrationTest extends BigTest {

  @Test
  public void shouldReturnCategoryNewsWhenUserHasUserRole() throws Exception {
    Long newsId = saveNews().getId();

    mockMvc.perform(get("/news/{id}", String.valueOf(newsId))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("My first News!!")))
        .andExpect(jsonPath("$.text", equalTo("News content.")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/news.jpg")))
        .andExpect(status().isOk());

    cleanNewsData();
  }

  @Test
  public void shouldReturnCategoryNewsWhenUserHasAdminRole() throws Exception {
    Long newsId = saveNews().getId();

    mockMvc.perform(get("/news/{id}", String.valueOf(newsId))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("My first News!!")))
        .andExpect(jsonPath("$.text", equalTo("News content.")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/news.jpg")))
        .andExpect(status().isOk());

    cleanNewsData();
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(get("/news/{id}", "1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenNewsDoesNotExist() throws Exception {
    mockMvc.perform(get("/news/{id}", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("News not found.")))
        .andExpect(status().isNotFound());
  }
}
