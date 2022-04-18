package com.alkemy.ong.bigtest.category;

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

public class GetCategoryDetailsIntegrationTest extends BigTest {

  @Test
  public void shouldReturnCategoryWhenHasUserRole() throws Exception {
    Long categoryId = getRandomCategoryId();

    mockMvc.perform(get("/categories/{id}", String.valueOf(categoryId))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("Name Category")))
        .andExpect(jsonPath("$.description", equalTo("Description Category")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/category.jpg")))
        .andExpect(status().isOk());

    deleteCategory(categoryId);
  }

  @Test
  public void shouldReturnCategoryWhenHasAdminRole() throws Exception {
    Long categoryId = getRandomCategoryId();

    mockMvc.perform(get("/categories/{id}", String.valueOf(categoryId))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("Name Category")))
        .andExpect(jsonPath("$.description", equalTo("Description Category")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/category.jpg")))
        .andExpect(status().isOk());

    deleteCategory(categoryId);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(get("/categories/{id}", "1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenCategoryDoesNotExist() throws Exception {
    mockMvc.perform(get("/categories/{id}", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Category not found.")))
        .andExpect(status().isNotFound());
  }

}
