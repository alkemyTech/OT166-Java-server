package com.alkemy.ong.bigtest.testimonial;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.CreateTestimonialRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.TestimonialEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class CreateTestimonialIntegrationTest extends BigTest {

  @Test
  public void shouldCreateTestimonialWhenUserHasStandardUserRole() throws Exception {
    String response = mockMvc.perform(post("/testimonials")
            .content(getContent("Testimonial Name", "Testimonial Content",
                "https://s3.com/testimonial.jpg"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("Testimonial Name")))
        .andExpect(jsonPath("$.content", equalTo("Testimonial Content")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/testimonial.jpg")))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    Integer testimonialId = JsonPath.read(response, "$.id");
    assertTestimonialHasBeenCreated(Long.valueOf(testimonialId));
  }

  @Test
  public void shouldCreateTestimonialWhenUserHasAdminRole() throws Exception {
    String response = mockMvc.perform(post("/testimonials")
            .content(getContent("Testimonial Name", "Testimonial Content",
                "https://s3.com/testimonial.jpg"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("Testimonial Name")))
        .andExpect(jsonPath("$.content", equalTo("Testimonial Content")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/testimonial.jpg")))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    Integer testimonialId = JsonPath.read(response, "$.id");
    assertTestimonialHasBeenCreated(Long.valueOf(testimonialId));
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(post("/testimonials")
            .content(getContent("Testimonial Name", "Testimonial Content",
                "https://s3.com/testimonial.jpg"))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsNull() throws Exception {
    mockMvc.perform(post("/testimonials")
            .content(getContent(null, "Testimonial Content",
                "https://s3.com/testimonial.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Name cannot be null or empty.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenContentIsNull() throws Exception {
    mockMvc.perform(post("/testimonials")
            .content(getContent("Testimonial Name", null,
                "https://s3.com/testimonial.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Content cannot be null or empty.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsTooLong() throws Exception {
    String nameTooLong = RandomStringUtils.random(60, ".");

    mockMvc.perform(post("/testimonials")
            .content(getContent(nameTooLong, "Testimonial Content",
                "https://s3.com/testimonial.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(2)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("Name cannot have more than 50 characters.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenNameContainsNumbers() throws Exception {
    mockMvc.perform(post("/testimonials")
            .content(getContent("Nam3 whit numb3rs", "Testimonial Content",
                "https://s3.com/testimonial.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("Name can only have alphabetic characters with spaces.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenContentIsTooLong() throws Exception {
    String contentTooLong = RandomStringUtils.random(160, "a");

    mockMvc.perform(post("/testimonials")
            .content(getContent("Testimonial Name", contentTooLong,
                "https://s3.com/testimonial.jpg"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("Content cannot have more than 150 characters.")))
        .andExpect(status().isBadRequest());
  }

  private String getContent(String name, String content, String image)
      throws JsonProcessingException {
    return objectMapper.writeValueAsString(CreateTestimonialRequest.builder()
        .name(name)
        .content(content)
        .image(image)
        .build());
  }

  private void assertTestimonialHasBeenCreated(Long testimonialId) {
    Optional<TestimonialEntity> optionalTestimonialEntity =
        testimonialRepository.findById(testimonialId);
    assertTrue(optionalTestimonialEntity.isPresent());
    assertEquals("Testimonial Name", optionalTestimonialEntity.get().getName());
    assertEquals("https://s3.com/testimonial.jpg",
        optionalTestimonialEntity.get().getImage());
    assertEquals("Testimonial Content", optionalTestimonialEntity.get().getContent());
    cleanTestimonialData(optionalTestimonialEntity.get());
  }
}
