package com.alkemy.ong.bigtest.contact;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.ContactEntity;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ListActiveContactsIntegrationTest extends BigTest{

  @Test
  public void  shouldReturnListOfContactsWhenUserHasHasAdminUserRole() throws Exception {
    ContactEntity randomContact = getRandomContact();

    mockMvc.perform(get("/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.contacts[*].id")
            .value(hasItem(randomContact.getId().intValue())))
        .andExpect(jsonPath("$.contacts[*].name")
            .value(hasItem(randomContact.getName())))
        .andExpect(jsonPath("$.contacts[*].phone")
            .value(hasItem(randomContact.getPhone())))
        .andExpect(jsonPath("$.contacts[*].message")
            .value(hasItem(randomContact.getMessage())));

    cleanContactData(randomContact);
  }

  @Test
  public void  shouldReturnEmptyListOfContactsWhenContactsIsEmpty() throws Exception {
    mockMvc.perform(get("/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.contacts")
            .value(empty()));

  }

}
