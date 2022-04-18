package com.alkemy.ong.bigtest.category;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

public class ListCategoryIntegrationTest extends BigTest {

  @Test
  public void shouldReturnListOfCategoriesWhenUserHasAdminRole() throws Exception {
    CategoryEntity randomCategory = getRandomCategory();

    MockHttpServletResponse response = mockMvc.perform(get("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.categories[*].id", notNullValue()))
        .andExpect(jsonPath("$.categories[*].name").value(hasItem("Name Category")))
        .andExpect(jsonPath("$.categories[*].description")
            .value(hasItem("Description Category")))
        .andExpect(jsonPath("$.categories[*].image")
            .value(hasItem("https://s3.com/category.jpg")))
        .andExpect(jsonPath("$.categories", hasSize(1)))
        .andExpect(status().isOk()).andReturn().getResponse();

    cleanCategoryData(randomCategory);
  }

  @Test
  public void shouldReturnListOfCategoriesWhenUserHasStandardUserRole() throws Exception {
    CategoryEntity randomCategory = getRandomCategory();

    MockHttpServletResponse response = mockMvc.perform(get("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.categories[*].id", notNullValue()))
        .andExpect(jsonPath("$.categories[*].name").value(hasItem("Name Category")))
        .andExpect(jsonPath("$.categories[*].description")
            .value(hasItem("Description Category")))
        .andExpect(jsonPath("$.categories[*].image")
            .value(hasItem("https://s3.com/category.jpg")))
        .andExpect(jsonPath("$.categories", hasSize(1)))
        .andExpect(status().isOk()).andReturn().getResponse();

    cleanCategoryData(randomCategory);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(get("/categories")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnEmptyListOfCategoriesWhenCategoriesIsEmpty() throws Exception {
    MockHttpServletResponse response = mockMvc.perform(get("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.categories").value(empty()))
        .andExpect(status().isOk()).andReturn().getResponse();

  }
}
