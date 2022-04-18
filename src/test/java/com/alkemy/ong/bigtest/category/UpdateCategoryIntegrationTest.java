package com.alkemy.ong.bigtest.category;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.CreateCategoryRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class UpdateCategoryIntegrationTest extends BigTest {

  @Test
  public void shouldUpdateCategoryWhenUserHasAdminRole() throws Exception {
    CategoryEntity randomCategory = getRandomCategory();
    Long randomCategoryId = randomCategory.getId();

    mockMvc.perform(put("/categories/{id}", String.valueOf(randomCategoryId))
            .content(getContent("New name", "New description",
                "https://s3.com/mycategory.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("New name")))
        .andExpect(jsonPath("$.description", equalTo("New description")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/mycategory.jpg")))
        .andExpect(status().isOk());

    assertCategoryHasBeenUpdated(randomCategoryId);
    cleanCategoryData(randomCategory);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    CategoryEntity randomCategory = getRandomCategory();
    Long randomCategoryId = randomCategory.getId();

    mockMvc.perform(put("/categories/{id}", String.valueOf(randomCategoryId))
            .content(getContent("New name", "New description",
                "https://s3.com/mycategory.jpg"))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());

    cleanCategoryData(randomCategory);
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenCategoryNotExist() throws Exception {
    String nonExistCategoryId = "1000000";

    mockMvc.perform(put("/categories/{id}", nonExistCategoryId)
            .content(getContent("New name", "New description",
                "https://s3.com/mycategory.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Category not found.")))
        .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsNull() throws Exception {
    CategoryEntity randomCategory = getRandomCategory();
    Long randomCategoryId = randomCategory.getId();

    mockMvc.perform(put("/categories/{id}", String.valueOf(randomCategoryId))
            .content(getContent(null, "New description",
                "https://s3.com/mycategory.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name must not be empty or null.")))
        .andExpect(status().isBadRequest());

    cleanCategoryData(randomCategory);
  }

  @Test
  public void shouldReturnBadRequestWhenNameContainsNumbers() throws Exception {
    CategoryEntity randomCategory = getRandomCategory();
    Long randomCategoryId = randomCategory.getId();

    mockMvc.perform(put("/categories/{id}", String.valueOf(randomCategoryId))
            .content(getContent("Nam3 whit numb3rs", "New description",
                "https://s3.com/mycategory.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name has an invalid format.")))
        .andExpect(status().isBadRequest());

    cleanCategoryData(randomCategory);
  }

  private String getContent(String name, String description, String image)
      throws JsonProcessingException {
    return objectMapper.writeValueAsString(CreateCategoryRequest.builder()
        .name(name)
        .description(description)
        .image(image)
        .build());
  }

  private void assertCategoryHasBeenUpdated(Long categoryId) {
    Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryId);
    assertTrue(optionalCategoryEntity.isPresent());
    assertEquals("New name", optionalCategoryEntity.get().getName());
    assertEquals("New description", optionalCategoryEntity.get().getDescription());
    assertEquals("https://s3.com/mycategory.jpg", optionalCategoryEntity.get().getImage());
    cleanCategoryData(optionalCategoryEntity.get());
  }
}
