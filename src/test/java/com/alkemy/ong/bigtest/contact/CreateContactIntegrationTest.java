package com.alkemy.ong.bigtest.contact;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import com.alkemy.ong.application.rest.request.CreateContactRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.ContactEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class CreateContactIntegrationTest extends BigTest {

  @Test
  public void shouldCreateContactWhenRequestUserHasAdminRole() throws Exception{
    String response = mockMvc.perform(post("/contacts")
            .content(getContent("lucas","1550508080","lucas@gmail.com","mi mensaje"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.id",notNullValue()))
        .andExpect(jsonPath("$.name",equalTo("lucas")))
        .andExpect(jsonPath("$.phone",equalTo("1550508080")))
        .andExpect(jsonPath("$.message",equalTo("mi mensaje")))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    
    Integer contactId = JsonPath.read(response, "$.id");
    assertContactHasBeenCreated(Long.valueOf(contactId));
  }

  @Test
  public void shouldCreateContactWhenRequestUserHasStandardUserRole() throws Exception{
    String response = mockMvc.perform(post("/contacts")
            .content(getContent("lucas","1550508080","lucas@gmail.com","mi mensaje"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.id",notNullValue()))
        .andExpect(jsonPath("$.name",equalTo("lucas")))
        .andExpect(jsonPath("$.phone",equalTo("1550508080")))
        .andExpect(jsonPath("$.message",equalTo("mi mensaje")))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    
    Integer contactId = JsonPath.read(response, "$.id");
    assertContactHasBeenCreated(Long.valueOf(contactId));
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(post("/contacts")
        .content(getContent("lucas","1550508080","lucas@gmail.com","mi mensaje"))
        .contentType(MediaType.APPLICATION_JSON_VALUE))
    .andExpect(jsonPath("$.statusCode", equalTo(403)))
    .andExpect(jsonPath("$.message",
        equalTo("Access denied. Please, try to login again or contact your admin.")))
    .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsNull() throws Exception{
    mockMvc.perform(post("/contacts")
        .content(getContent(null,"1550508080","lucas@gmail.com","mi mensaje"))
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
    .andExpect(jsonPath("$.statusCode",equalTo(400)))
    .andExpect(jsonPath("$.message",equalTo("Invalid input data.")))
    .andExpect(jsonPath("$.moreInfo",hasSize(1)))
    .andExpect(jsonPath("$.moreInfo",hasItem("The name must not be null")))
    .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenNameHasInvalidFormat() throws Exception{
    mockMvc.perform(post("/contacts")
        .content(getContent("lucas99","1550508080","lucas@gmail.com","mi mensaje"))
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
    .andExpect(jsonPath("$.statusCode",equalTo(400)))
    .andExpect(jsonPath("$.message",equalTo("Invalid input data.")))
    .andExpect(jsonPath("$.moreInfo",hasSize(1)))
    .andExpect(jsonPath("$.moreInfo",hasItem("The name has invalid format.")))
    .andExpect(status().isBadRequest());
  }
  
  @Test
  public void shouldReturnBadRequestWhenEmailIsNull() throws Exception{
    mockMvc.perform(post("/contacts")
        .content(getContent("lucas","1550508080",null,"mi mensaje"))
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
    .andExpect(jsonPath("$.statusCode",equalTo(400)))
    .andExpect(jsonPath("$.message",equalTo("Invalid input data.")))
    .andExpect(jsonPath("$.moreInfo",hasSize(1)))
    .andExpect(jsonPath("$.moreInfo",hasItem("The email must not be empty.")))
    .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenMessageIsNull() throws Exception{
    mockMvc.perform(post("/contacts")
        .content(getContent("lucas","1550508080","lucas@gmail.com",null))
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
    .andExpect(jsonPath("$.statusCode",equalTo(400)))
    .andExpect(jsonPath("$.message",equalTo("Invalid input data.")))
    .andExpect(jsonPath("$.moreInfo",hasSize(1)))
    .andExpect(jsonPath("$.moreInfo",hasItem("The message must not be empty.")))
    .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenMessageHasInvalidFormat() throws Exception{
    mockMvc.perform(post("/contacts")
        .content(getContent("lucas","1550508080","lucas@gmail.com","mi mensaje#$%"))
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
    .andExpect(jsonPath("$.statusCode",equalTo(400)))
    .andExpect(jsonPath("$.message",equalTo("Invalid input data.")))
    .andExpect(jsonPath("$.moreInfo",hasSize(1)))
    .andExpect(jsonPath("$.moreInfo",hasItem("The message accepts only alphanumeric characters and blank spaces.")))
    .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenMessageIsTooLong() throws Exception{
    String nameTooLong = RandomStringUtils.random(151, "Lorem Ipsum 123 ");
    
    mockMvc.perform(post("/contacts")
        .content(getContent("lucas","1550508080","lucas@gmail.com",nameTooLong))
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
    .andExpect(jsonPath("$.statusCode",equalTo(400)))
    .andExpect(jsonPath("$.message",equalTo("Invalid input data.")))
    .andExpect(jsonPath("$.moreInfo",hasSize(1)))
    .andExpect(jsonPath("$.moreInfo",hasItem("The message must be 150 characters or less")))
    .andExpect(status().isBadRequest());
  }

  private String getContent(String name, String phone, String email, String message) 
      throws JsonProcessingException {  
    return objectMapper.writeValueAsString(CreateContactRequest.builder()
        .name(name)
        .phone(phone)
        .email(email)
        .message(message)
        .build());
  }

  private void assertContactHasBeenCreated(Long contactId) {
    Optional<ContactEntity> optionalContactEntity = contactRepository.findById(contactId);
    assertTrue(optionalContactEntity.isPresent());
    assertEquals("lucas", optionalContactEntity.get().getName());
    assertEquals("1550508080", optionalContactEntity.get().getPhone());
    assertEquals("mi mensaje", optionalContactEntity.get().getMessage());
    cleanContactData(optionalContactEntity.get());
  }
  
}
