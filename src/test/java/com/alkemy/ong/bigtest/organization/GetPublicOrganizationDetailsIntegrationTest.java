package com.alkemy.ong.bigtest.organization;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import org.junit.Test;
import org.springframework.http.MediaType;

public class GetPublicOrganizationDetailsIntegrationTest extends BigTest {

  @Test
  public void shouldReturnErrorResponseWhenNonOrganizationRecordIsRetrieved() throws Exception {
    mockMvc.perform(get("/organization/public")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(404)))
        .andExpect(jsonPath("$.message", equalTo("Entity not found.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Missing record in organization table.")))
        .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturnOrganizationDetailsWhenOrganizationRecordIsRetrieved() throws Exception {
    saveOrganizationDetails();
    saveSlide();
    mockMvc.perform(get("/organization/public")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name", equalTo("Somos Mas")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/logo.jpg/")))
        .andExpect(jsonPath("$.phone", equalTo("+5411345678")))
        .andExpect(jsonPath("$.address", equalTo("Elm Street 3")))
        .andExpect(
            jsonPath("$.socialMedia.facebookUrl", equalTo("https://www.facebook.com/Somos_Mas/")))
        .andExpect(jsonPath("$.socialMedia.linkedInUrl",
            equalTo("https://www.linkedin.com/in/Somos-Mas/")))
        .andExpect(
            jsonPath("$.socialMedia.instagramUrl", equalTo("https://www.instagram.com/SomosMas/")))
        .andExpect(status().isOk());
  }

}
