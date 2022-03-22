package com.alkemy.ong.bigtest.organization;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.OngApplication;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import com.alkemy.ong.infrastructure.database.repository.IOrganizationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.main.allow-bean-definition-overriding=true",
    classes = OngApplication.class)
@AutoConfigureMockMvc
public class GetPublicOrganizationDetailsIntegrationTest {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  private IOrganizationRepository organizationRepository;

  @Before
  public void setup() {
    organizationRepository.deleteAll();
  }

  @After
  public void tearDown() {
    organizationRepository.deleteAll();
  }

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

  private void saveOrganizationDetails() {
    organizationRepository.save(OrganizationEntity.builder()
        .name("Somos Mas")
        .image("https://s3.com/logo.jpg/")
        .welcomeText("Welcome to Somos Mas")
        .email("somos.mas@ong.com")
        .phone("+5411345678")
        .address("Elm Street 3")
        .build());
  }

}
