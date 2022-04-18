package com.alkemy.ong.bigtest.category;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import java.util.Optional;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class DeleteCategoryIntegrationTest extends BigTest {

  @Test
  public void shouldDeleteCategoryWhenUserHasAdminRole() throws Exception {
    Long id = saveCategory("news").getId();

    mockMvc.perform(delete("/categories/{id}", String.valueOf(id))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(status().isNoContent());

    assertCategoryHasBeenDeleted(id);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenUserHasStandardRole() throws Exception {
    mockMvc.perform(delete("/categories/{id}", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(delete("/categories/{id}", "1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenCategoryDoesNotExist() throws Exception {
    mockMvc.perform(delete("/categories/{id}", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Category not found.")))
        .andExpect(status().isNotFound());
  }

  private void assertCategoryHasBeenDeleted(Long id) {
    Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(id);
    assertTrue(optionalCategoryEntity.isPresent());
    assertEquals(true, optionalCategoryEntity.get().getSoftDeleted());
  }

}
