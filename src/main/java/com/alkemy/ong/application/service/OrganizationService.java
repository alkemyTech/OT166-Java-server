package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFound;
import com.alkemy.ong.application.rest.response.OrganizationResponse;
import com.alkemy.ong.application.service.abstraction.IOrganizationService;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IOrganizationMapper;
import com.alkemy.ong.infrastructure.database.repository.IOrganizationRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationService implements IOrganizationService {

  @Autowired
  private IOrganizationRepository organizationRepository;
  @Autowired
  private IOrganizationMapper organizationMapper;


  @Override
  public OrganizationResponse getPublicOrganization() throws Exception {

    List<OrganizationEntity> organizations = organizationRepository.findAll();
    if (organizations == null || organizations.isEmpty()) {
      throw new EntityNotFound("Data not found");
    }
    return organizationMapper.toOrganizationResponse(organizations.get(0));
  }
}
