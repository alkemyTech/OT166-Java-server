package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.response.OrganizationResponse;
import com.alkemy.ong.application.service.abstraction.IGetOrganizationDetailsService;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IOrganizationMapper;
import com.alkemy.ong.infrastructure.database.repository.IOrganizationRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class OrganizationService implements IGetOrganizationDetailsService {

  @Autowired
  private IOrganizationRepository organizationRepository;

  @Autowired
  private IOrganizationMapper organizationMapper;


  @Override
  public OrganizationResponse getPublicOrganizationDetails() {
    List<OrganizationEntity> organizations = organizationRepository.findAll();
    if (organizations.isEmpty()) {
      throw new EntityNotFoundException("Missing record in organization table.");
    }
    return organizationMapper.toOrganizationResponse(organizations.get(0));
  }

}
