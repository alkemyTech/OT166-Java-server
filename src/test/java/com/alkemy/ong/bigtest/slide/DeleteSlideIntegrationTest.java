package com.alkemy.ong.bigtest.slide;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.SlideEntity;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class DeleteSlideIntegrationTest extends BigTest {

  @Test
  public void shouldDeleteSlideWhenUserHasAdminRole() throws Exception {
    Long id = saveSlide().getId();

    mockMvc.perform(delete("/slides/{id}", String.valueOf(id))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(status().isNoContent());

    assertSlideHasBeenDeleted(id);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenUserHasStandardRole() throws Exception {
    mockMvc.perform(delete("/slides/{id}", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenSlideDoesNotExist() throws Exception {
    mockMvc.perform(delete("/slides/{id}", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Slide not found.")))
        .andExpect(status().isNotFound());
  }

  private void assertSlideHasBeenDeleted(Long id) {
    Optional<SlideEntity> optionalSlideEntity = slideRepository.findById(id);
    Assertions.assertTrue(optionalSlideEntity.isEmpty());
  }

}
