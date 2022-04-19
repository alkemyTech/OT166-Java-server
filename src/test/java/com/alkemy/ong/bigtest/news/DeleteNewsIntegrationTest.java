package com.alkemy.ong.bigtest.news;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import java.util.Optional;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class DeleteNewsIntegrationTest extends BigTest {

  @Test
  public void shouldDeleteNewsWhenRequestUserHasAdminRole() throws Exception {
    Long newsId = saveNews().getId();

    mockMvc.perform(delete("/news/{id}", String.valueOf(newsId))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(status().isNoContent());

    assertNewsHasBeenDeleted(newsId);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    Long newsId = 999L;

    mockMvc.perform(delete("/news/{id}", String.valueOf(newsId))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenNewsDoesNotExist() throws Exception {
    mockMvc.perform(delete("/news/{id}", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("News not found.")))
        .andExpect(status().isNotFound());
  }

  private void assertNewsHasBeenDeleted(Long id) {
    Optional<NewsEntity> optionalNewsEntity = newsRepository.findById(id);
    assertTrue(optionalNewsEntity.isPresent());
    assertEquals(true, optionalNewsEntity.get().getSoftDeleted());
    cleanNewsData();
  }
}
