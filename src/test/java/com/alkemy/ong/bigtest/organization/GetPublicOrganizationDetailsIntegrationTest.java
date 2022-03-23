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
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Missing record in organization table.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("Entity not found.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnOrganizationDetailsWhenOrganizationRecordIsRetrieved() throws Exception {
    saveOrganizationDetails();

    mockMvc.perform(get("/organization/public")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name", equalTo("Somos Mas")))
        .andExpect(jsonPath("$.image", equalTo("https://s3.com/logo.jpg/")))
        .andExpect(jsonPath("$.phone", equalTo("+5411345678")))
        .andExpect(jsonPath("$.address", equalTo("Elm Street 3")))
        .andExpect(status().isOk());
  }

}