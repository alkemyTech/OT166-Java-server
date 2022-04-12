package com.alkemy.ong.bigtest.contact;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.Matchers.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.ContactEntity;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ListActiveContactsIntegrationTest extends BigTest{

  @Test
  public void  shouldReturnListOfContactsWhenUserHasAdminUserRole() throws Exception {
    ContactEntity randomContact = getRandomContact();
    
    mockMvc.perform(get("/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.contacts[*].id")
            .value(hasItem(randomContact.getId().intValue())))
        .andExpect(jsonPath("$.contacts[*].name")
            .value(hasItem("juan")))
        .andExpect(jsonPath("$.contacts[*].phone")
            .value(hasItem("159028080")))
        .andExpect(jsonPath("$.contacts[*].message")
            .value(hasItem("my message")))
        .andExpect(jsonPath("$.contacts",hasSize(1)))
        .andExpect(status().isOk());

    cleanContactData(randomContact);
  }

  @Test
  public void  shouldReturnForbiddenErrorResponseWhenUserHasStandardUserRole() throws Exception {
    mockMvc.perform(get("/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForStandardUser()))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")));

  }

  @Test
  public void  shouldReturnEmptyListOfContactsWhenContactsIsEmpty() throws Exception {
    mockMvc.perform(get("/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.contacts")
            .value(empty()))
        .andExpect(status().isOk());

  }

}
