package com.alkemy.ong.bigtest.testimonial;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.TestimonialEntity;
import java.util.Optional;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class DeleteTestimonialIntegrationTest extends BigTest {

  @Test
  public void shouldDeleteTestimonialWhenRequestUserHasAdminRole() throws Exception {
    Long id = saveTestimonial().getId();

    mockMvc.perform(delete("/testimonials/{id}", String.valueOf(id))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(status().isNoContent());

    assertTestimonialHasBeenDeleted(id);
  }


  @Test
  public void shouldDeleteTestimonialWhenRequestUserHasStandardUserRole() throws Exception {
    Long id = saveTestimonial().getId();

    mockMvc.perform(delete("/testimonials/{id}", String.valueOf(id))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(status().isNoContent());

    assertTestimonialHasBeenDeleted(id);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    Long id = saveTestimonial().getId();

    mockMvc.perform(delete("/testimonials/{id}", String.valueOf(id))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnNotFoundErrorResponseWhenTestimonialDoesNotExist() throws Exception {
    mockMvc.perform(delete("/testimonials/{id}", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Testimonial not found.")))
        .andExpect(status().isNotFound());
  }

  private void assertTestimonialHasBeenDeleted(Long id) {
    Optional<TestimonialEntity> optionalTestimonialEntity = testimonialRepository.findById(id);
    assertTrue(optionalTestimonialEntity.isPresent());
    assertEquals(true, optionalTestimonialEntity.get().getSoftDelete());
  }
}
