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

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.alkemy.ong.application.rest.request.CreateContactRequest;
import com.alkemy.ong.application.util.mail.EmailDelegate;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.ContactEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class CreateContactIntegrationTest extends BigTest {

  private ListAppender<ILoggingEvent> logWatcher;

  @Test
  public void shouldCreateContactWhenRequestUserHasAdminRole() throws Exception {
    initLogWatcherEmailDelegate();
    String response = mockMvc.perform(post("/contacts")
            .content(getContent("Lucas", "1550508080", "lucas@gmail.com", "my message"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("Lucas")))
        .andExpect(jsonPath("$.phone", equalTo("1550508080")))
        .andExpect(jsonPath("$.message", equalTo("my message")))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    Integer contactId = JsonPath.read(response, "$.id");
    assertContactHasBeenCreated(Long.valueOf(contactId));
    assertEquals("SendGrid status code: 401", logWatcher.list.get(0).getFormattedMessage());
  }

  @Test
  public void shouldCreateContactWhenRequestUserHasStandardUserRole() throws Exception {
    initLogWatcherEmailDelegate();
    String response = mockMvc.perform(post("/contacts")
            .content(getContent("Lucas", "1550508080", "lucas@gmail.com", "my message"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("Lucas")))
        .andExpect(jsonPath("$.phone", equalTo("1550508080")))
        .andExpect(jsonPath("$.message", equalTo("my message")))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    Integer contactId = JsonPath.read(response, "$.id");
    assertContactHasBeenCreated(Long.valueOf(contactId));
    assertEquals("SendGrid status code: 401", logWatcher.list.get(0).getFormattedMessage());
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    mockMvc.perform(post("/contacts")
            .content(getContent("Lucas", "1550508080", "lucas@gmail.com", "my message"))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnBadRequestWhenNameIsNull() throws Exception {
    mockMvc.perform(post("/contacts")
            .content(getContent(null, "1550508080", "lucas@gmail.com", "my message"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name must not be null")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenNameHasInvalidFormat() throws Exception {
    mockMvc.perform(post("/contacts")
            .content(getContent("lucas99", "1550508080", "lucas@gmail.com", "my message"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The name has invalid format.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenEmailIsNull() throws Exception {
    mockMvc.perform(post("/contacts")
            .content(getContent("Lucas", "1550508080", null, "my message"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The email must not be empty.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenMessageIsNull() throws Exception {
    mockMvc.perform(post("/contacts")
            .content(getContent("Lucas", "1550508080", "lucas@gmail.com", null))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The message must not be empty.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenMessageHasInvalidFormat() throws Exception {
    mockMvc.perform(post("/contacts")
            .content(getContent("Lucas", "1550508080", "lucas@gmail.com", "my message#$%"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo",
            hasItem("The message accepts only alphanumeric characters and blank spaces.")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenMessageIsTooLong() throws Exception {
    String nameTooLong = RandomStringUtils.random(151, "Lorem Ipsum 123 ");

    mockMvc.perform(post("/contacts")
            .content(getContent("Lucas", "1550508080", "lucas@gmail.com", nameTooLong))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.statusCode", equalTo(400)))
        .andExpect(jsonPath("$.message", equalTo("Invalid input data.")))
        .andExpect(jsonPath("$.moreInfo", hasSize(1)))
        .andExpect(jsonPath("$.moreInfo", hasItem("The message must be 150 characters or less")))
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
    assertEquals("Lucas", optionalContactEntity.get().getName());
    assertEquals("1550508080", optionalContactEntity.get().getPhone());
    assertEquals("my message", optionalContactEntity.get().getMessage());
    cleanContactData(optionalContactEntity.get());
  }

  private void initLogWatcherEmailDelegate() {
    this.logWatcher = new ListAppender<>();
    this.logWatcher.start();
    ((Logger) LoggerFactory.getLogger(EmailDelegate.class)).addAppender(this.logWatcher);
  }

}
