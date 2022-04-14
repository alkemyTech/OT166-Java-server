package com.alkemy.ong.bigtest.organization;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.application.rest.request.UpdateOrganizationRequest;
import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class UpdatePublicOrganizationDetailsIntegrationTest extends BigTest {

  @Test
  public void shouldUpdateOrganizationWhenUserHasAdminRole() throws Exception {
    OrganizationEntity ramdomOrganization = getRamdomOrganization();
    Long randomOrganizationId = ramdomOrganization.getId();

    mockMvc.perform(patch("/organization/public", String.valueOf(randomOrganizationId))
            .content(getContent("Somos Mas", "Elm Street 3", "+5411345678", "somos.mas@ong.com"))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        //.andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("Somos Mas")))
        .andExpect(jsonPath("$.address", equalTo("Elm Street 3")))
        .andExpect(jsonPath("$.phone", equalTo("+5411345678")))
        .andExpect(jsonPath("$.email", equalTo("somos.mas@ong.com")))
        .andExpect(status().isOk());

    assertOrganizationHasBeenUpdated(randomOrganizationId);
    cleanOrganizationData(ramdomOrganization);
  }

  @Test
  public void shouldReturnForbiddenErrorResponseWhenTokenIsNotSent() throws Exception {
    OrganizationEntity ramdomOrganization = getRamdomOrganization();
    Long randomOrganizationId = ramdomOrganization.getId();

    mockMvc.perform(patch("/organization/public", String.valueOf(randomOrganizationId))
            .content(getContent("Somos Mas", "Elm Street 3", "+5411345678", "somos.mas@ong.com"))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.statusCode", equalTo(403)))
        .andExpect(jsonPath("$.message",
            equalTo("Access denied. Please, try to login again or contact your admin.")))
        .andExpect(status().isForbidden());

    cleanOrganizationData(ramdomOrganization);
  }

  private String getContent(String name, String address, String phone, String email)
      throws JsonProcessingException {
    return objectMapper.writeValueAsString(UpdateOrganizationRequest.builder()
        .name(name)
        .address(address)
        .phone(phone)
        .email(email)
        .build());
  }

  private void assertOrganizationHasBeenUpdated(Long organizationId) {
    Optional<OrganizationEntity> optionalOrganizationEntityEntity = organizationRepository.findById(
        organizationId);
    assertTrue(optionalOrganizationEntityEntity.isPresent());
    assertEquals("Somos Mas", optionalOrganizationEntityEntity.get().getName());
    assertEquals("Elm Street 3", optionalOrganizationEntityEntity.get().getAddress());
    assertEquals("+5411345678", optionalOrganizationEntityEntity.get().getPhone());
    assertEquals("somos.mas@ong.com", optionalOrganizationEntityEntity.get().getEmail());
    cleanOrganizationData(optionalOrganizationEntityEntity.get());
  }

}
