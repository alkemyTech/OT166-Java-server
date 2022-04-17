package com.alkemy.ong.bigtest.testimonial;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.TestimonialEntity;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ListTestimonialIntegrationTest extends BigTest {

  @Test
  public void shouldReturnListOfTestimonialWhenUserHasAdminRole() throws Exception {
    TestimonialEntity randomTestimonial = getRandomTestimonial();

    mockMvc.perform(get("/testimonials")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.testimonials[*].id", notNullValue()))
        .andExpect(jsonPath("$.testimonials[*].name")
            .value(hasItem("Name Testimonial")))
        .andExpect(jsonPath("$.testimonials[*].image")
            .value(hasItem("https://s3.com/testimonial.jpg")))
        .andExpect(jsonPath("$.testimonials[*].content")
            .value(hasItem("Content Testimonial")))
        .andExpect(status().isOk());

    cleanTestimonialData(randomTestimonial);
  }

  @Test
  public void shouldReturnListOfTestimonialWhenUserHasStandardUserRole() throws Exception {
    TestimonialEntity randomTestimonial = getRandomTestimonial();

    mockMvc.perform(get("/testimonials")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.testimonials[*].id", notNullValue()))
        .andExpect(jsonPath("$.testimonials[*].name")
            .value(hasItem("Name Testimonial")))
        .andExpect(jsonPath("$.testimonials[*].image")
            .value(hasItem("https://s3.com/testimonial.jpg")))
        .andExpect(jsonPath("$.testimonials[*].content")
            .value(hasItem("Content Testimonial")))
        .andExpect(status().isOk());

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

}
