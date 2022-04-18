package com.alkemy.ong.bigtest.category;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.CreateCategoryRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class CreateCategoryIntegrationTest extends BigTest {

  @Test
  public void shouldCreateCategoryWhenRequestUserHasAdminRole() throws Exception {
    String response = mockMvc.perform(post("/categories")
            .content(getContent("Category Name", "Category Description",
                "https://s3.com/category.jpg"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("Category Name")))
        .andExpect(jsonPath("$.description", equalTo("Category Description")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/category.jpg")))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    Integer categoryId = JsonPath.read(response, "$.id");
    assertCategoryHasBeenCreated(Long.valueOf(categoryId));
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(post("/categories")
            .content(getContent("Category Name", "Category Description",
                "https://s3.com/category.jpg"))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsNull() throws Exception {
    mockMvc.perform(post("/categories")
            .content(getContent(null, "Category Description",
                  "https://s3.com/category.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", IsEqual.equalTo(400)))
        .andExpect(jsonPath("$.message", IsEqual.equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name must not be empty or null.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenNameContainsNumbers() throws Exception {
    mockMvc.perform(post("/categories")
            .content(getContent("Nam3 whit numb3rs", "Category Description",
                  "https://s3.com/category.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", IsEqual.equalTo(400)))
        .andExpect(jsonPath("$.message", IsEqual.equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name has an invalid format.")))
        .andExpect(status().isBadRequest());
  }

  private String getContent(String name, String description, String image)
      throws JsonProcessingException {
    return objectMapper.writeValueAsString(CreateCategoryRequest.builder()
        .name(name)
        .description(description)
        .image(image)
        .build());
  }

  private void assertCategoryHasBeenCreated(Long categoryId) {
    Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryId);
    assertTrue(optionalCategoryEntity.isPresent());
    assertEquals("Category Name", optionalCategoryEntity.get().getName());
    assertEquals("Category Description", optionalCategoryEntity.get().getDescription());
    assertEquals("https://s3.com/category.jpg", optionalCategoryEntity.get().getImage());
    cleanCategoryData(optionalCategoryEntity.get());
  }

}
