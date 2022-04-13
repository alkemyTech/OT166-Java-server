package com.alkemy.ong.bigtest.testimonial;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.CreateTestimonialRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.TestimonialEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class UpdateTestimonialIntegrationTest extends BigTest {

  @Test
  public void shouldUpdateTestimonialWhenUserHasAdminRole() throws Exception {
    TestimonialEntity randomTestimonial = getRandomTestimonial();
    Long randomTestimonialId = randomTestimonial.getId();

    mockMvc.perform(put("/testimonials/{id}", String.valueOf(randomTestimonialId))
            .content(getContent("New name", "New content", ""))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("New name")))
        .andExpect(jsonPath("$.content", equalTo("New content")))
        .andExpect(jsonPath("$.image", equalTo("")))
        .andExpect(status().isOk());

    assertTestimonialHasBeenUpdated(randomTestimonialId);
    cleanTestimonialData(randomTestimonial);
  }

  @Test
  public void shouldUpdateTestimonialWhenUserHasStandardUserRole() throws Exception {
    TestimonialEntity randomTestimonial = getRandomTestimonial();
    Long randomTestimonialId = randomTestimonial.getId();

    mockMvc.perform(put("/testimonials/{id}", String.valueOf(randomTestimonialId))
            .content(getContent("New name", "New content", ""))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("New name")))
        .andExpect(jsonPath("$.content", equalTo("New content")))
        .andExpect(jsonPath("$.image", equalTo("")))
        .andExpect(status().isOk());

    assertTestimonialHasBeenUpdated(randomTestimonialId);
    cleanTestimonialData(randomTestimonial);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    TestimonialEntity randomTestimonial = getRandomTestimonial();
    Long randomTestimonialId = randomTestimonial.getId();

    mockMvc.perform(put("/testimonials/{id}", String.valueOf(randomTestimonialId))
            .content(getContent("New name", "New content", ""))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());

    cleanTestimonialData(randomTestimonial);
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenTestimonialNotExist() throws Exception {
    String nonExistTestimonialId = "1000000";

    mockMvc.perform(put("/testimonials/{id}", nonExistTestimonialId)
            .content(getContent("New name", "New content", ""))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Testimonial not found.")))
        .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsNull() throws Exception {
    TestimonialEntity randomTestimonial = getRandomTestimonial();
    Long randomTestimonialId = randomTestimonial.getId();

    mockMvc.perform(put("/testimonials/{id}", String.valueOf(randomTestimonialId))
            .content(getContent(null, "New content", ""))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name must not be null")))
        .andExpect(status().isBadRequest());

    cleanTestimonialData(randomTestimonial);
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsTooLong() throws Exception {
    TestimonialEntity randomTestimonial = getRandomTestimonial();
    Long randomTestimonialId = randomTestimonial.getId();
    String nameTooLong = RandomStringUtils.random(60, ".");

    mockMvc.perform(put("/testimonials/{id}", String.valueOf(randomTestimonialId))
            .content(getContent(nameTooLong, "New content", ""))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(2)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The name attribute must not be more than 50 characters")))
        .andExpect(status().isBadRequest());

    cleanTestimonialData(randomTestimonial);
  }

  @Test
  public void shouldReturnBadRequestWhenContentIsTooLong() throws Exception {
    TestimonialEntity randomTestimonial = getRandomTestimonial();
    Long randomTestimonialId = randomTestimonial.getId();
    String contentTooLong = RandomStringUtils.random(160, "a");

    mockMvc.perform(put("/testimonials/{id}", String.valueOf(randomTestimonialId))
            .content(getContent("New name", contentTooLong, ""))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The name attribute must not be more than 150 characters")))
        .andExpect(status().isBadRequest());

    cleanTestimonialData(randomTestimonial);
  }

  @Test
  public void shouldReturnBadRequestWhenNameContainsNumbers() throws Exception {
    TestimonialEntity randomTestimonial = getRandomTestimonial();
    Long randomTestimonialId = randomTestimonial.getId();

    mockMvc.perform(put("/testimonials/{id}", String.valueOf(randomTestimonialId))
            .content(getContent("Nam3 whit numb3rs", "New content", ""))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name has invalid format.")))
        .andExpect(status().isBadRequest());

    cleanTestimonialData(randomTestimonial);
  }

  private String getContent(String name, String content, String image)
      throws JsonProcessingException {
    return objectMapper.writeValueAsString(CreateTestimonialRequest.builder()
        .name(name)
        .content(content)
        .image(image)
        .build());
  }

  private void assertTestimonialHasBeenUpdated(Long testimonialId) {
    Optional<TestimonialEntity> optionalTestimonialEntity = testimonialRepository.findById(testimonialId);
    assertTrue(optionalTestimonialEntity.isPresent());
    assertEquals("New name", optionalTestimonialEntity.get().getName());
    assertEquals("", optionalTestimonialEntity.get().getImage());
    assertEquals("New content", optionalTestimonialEntity.get().getContent());
    cleanTestimonialData(optionalTestimonialEntity.get());
  }

}
