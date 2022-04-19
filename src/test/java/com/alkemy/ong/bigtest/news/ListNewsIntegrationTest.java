package com.alkemy.ong.bigtest.news;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ListNewsIntegrationTest extends BigTest {

  @Test
  public void shouldReturnListOfNewsWhenUserHasAdminRole() throws Exception {
    createCategoryNews();
    saveNews();

    mockMvc.perform(get("/news")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.news[*].id", notNullValue()))
        .andExpect(jsonPath("$.news[*].name").value(hasItem("My first News!!")))
        .andExpect(jsonPath("$.news[*].image").value(hasItem("https://s3.com/news.jpg")))
        .andExpect(jsonPath("$.news[*].text").value(hasItem("News content.")))
        .andExpect(jsonPath("$.news", hasSize(1)))
        .andExpect(status().isOk());

    cleanNewsData();
    cleanCategoryData();
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(get("/news")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnEmptyListOfNewsWhenNewsIsEmpty() throws Exception {
    mockMvc.perform(get("/news")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.news").value(empty()))
        .andExpect(status().isOk());
  }
}
