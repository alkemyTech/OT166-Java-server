package com.alkemy.ong.bigtest.testimonial;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.response.ListTestimonialsResponse;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.TestimonialEntity;
import java.util.Objects;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

public class ListTestimonialIntegrationTest extends BigTest {

  @Test
  public void shouldReturnListOfTestimonialWhenUserHasAdminRole() throws Exception {
    TestimonialEntity randomTestimonial = getRandomTestimonial();

    MockHttpServletResponse response = mockMvc.perform(get("/testimonials")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.testimonials[*].id", notNullValue()))
        .andExpect(jsonPath("$.testimonials[*].name")
            .value(hasItem("Name Testimonial")))
        .andExpect(jsonPath("$.testimonials[*].image")
            .value(hasItem("https://s3.com/testimonial.jpg")))
        .andExpect(jsonPath("$.testimonials[*].content")
            .value(hasItem("Content Testimonial")))
        .andExpect(jsonPath("$.testimonials", hasSize(1)))
        .andExpect(status().isOk()).andReturn().getResponse();

    assertNotNull(response);
    cleanTestimonialData(randomTestimonial);
  }

  @Test
  public void shouldReturnListOfTestimonialWhenUserHasStandardUserRole() throws Exception {
    TestimonialEntity randomTestimonial = getRandomTestimonial();

    MockHttpServletResponse response = mockMvc.perform(get("/testimonials")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.testimonials[*].id", notNullValue()))
        .andExpect(jsonPath("$.testimonials[*].name")
            .value(hasItem("Name Testimonial")))
        .andExpect(jsonPath("$.testimonials[*].image")
            .value(hasItem("https://s3.com/testimonial.jpg")))
        .andExpect(jsonPath("$.testimonials[*].content")
            .value(hasItem("Content Testimonial")))
        .andExpect(jsonPath("$.testimonials", hasSize(1)))
        .andExpect(status().isOk()).andReturn().getResponse();

    assertNotNull(response);
    cleanTestimonialData(randomTestimonial);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(get("/testimonials")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnEmptyListOfTestimonialsWhenTestimonialsIsEmpty() throws Exception {
    MockHttpServletResponse response = mockMvc.perform(get("/testimonials")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.testimonials").value(empty()))
        .andExpect(status().isOk()).andReturn().getResponse();

    assertNotNull(response);
  }


}
