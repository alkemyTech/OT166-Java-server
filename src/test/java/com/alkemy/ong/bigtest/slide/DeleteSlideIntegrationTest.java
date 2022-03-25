package com.alkemy.ong.bigtest.slide;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.AuthenticationRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class DeleteSlideIntegrationTest extends BigTest {

  @Test
  public void shouldDeleteSlide() throws Exception {
    Long id = saveSlide();

    mockMvc.perform(delete("/slides/{id}", String.valueOf(id))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationToken()))
        .andExpect(status().isNoContent());
  }

}
