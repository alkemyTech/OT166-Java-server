package com.alkemy.ong.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.response.OrganizationResponse;
import com.alkemy.ong.application.service.abstraction.IGetSlideService;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IOrganizationMapper;
import com.alkemy.ong.infrastructure.database.repository.IOrganizationRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceTest {

  private static final String ORGANIZATION_NAME = "Somos Mas";

  private OrganizationService organizationService;
  
  @Mock
  private IOrganizationRepository organizationRepository;

  @Mock
  private IOrganizationMapper organizationMapper;
  
  @Mock
  private IGetSlideService getSlideService;

  @BeforeEach
  void setup() {
    organizationService = new OrganizationService(organizationRepository, organizationMapper, getSlideService);
  }

  @Test
  void shouldThrowExceptionWhenNonOrganizationRecordIsRetrieved() {
    given(organizationRepository.findAll()).willReturn(List.of());

    assertThrows(EntityNotFoundException.class,
        () -> organizationService.getPublicOrganizationDetails());
  }

  @Test
  void shouldReturnOrganizationDetailsWhenOrganizationRecordIsPresent() {
    OrganizationEntity organizationEntity = new OrganizationEntity();
    given(organizationRepository.findAll()).willReturn(List.of(organizationEntity));
    given(organizationMapper.toOrganizationResponse(organizationEntity))
        .willReturn(buildOrganizationStub());

    OrganizationResponse organizationResponse = organizationService.getPublicOrganizationDetails();

    assertNotNull(organizationResponse);
    verify(organizationRepository).findAll();
    verify(organizationMapper).toOrganizationResponse(organizationEntity);
    assertEquals(ORGANIZATION_NAME, organizationResponse.getName());
  }

  private OrganizationResponse buildOrganizationStub() {
    OrganizationResponse organizationResponse = new OrganizationResponse();
    organizationResponse.setName(ORGANIZATION_NAME);
    return organizationResponse;
  }

}
