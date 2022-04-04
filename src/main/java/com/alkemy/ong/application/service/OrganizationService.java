package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.request.SocialMediaRequest;
import com.alkemy.ong.application.rest.request.UpdateOrganizationRequest;
import com.alkemy.ong.application.rest.response.OrganizationResponse;
import com.alkemy.ong.application.service.abstraction.IGetOrganizationDetailsService;
import com.alkemy.ong.application.service.abstraction.IGetSlideService;
import com.alkemy.ong.application.service.abstraction.IUpdateOrganizationService;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IOrganizationMapper;
import com.alkemy.ong.infrastructure.database.repository.IOrganizationRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class OrganizationService implements IGetOrganizationDetailsService,
    IUpdateOrganizationService {

  @Autowired
  private IOrganizationRepository organizationRepository;

  @Autowired
  private IOrganizationMapper organizationMapper;

  @Autowired
  private IGetSlideService getSlideService;

  @Override
  public OrganizationResponse getPublicOrganizationDetails() {

    OrganizationResponse organizationResponse = new OrganizationResponse();
    organizationResponse = organizationMapper.toOrganizationResponse(findOrganization());
    organizationResponse.setSlides(getSlideService.list());

    return organizationResponse;
  }

  @Override
  public OrganizationResponse update(UpdateOrganizationRequest updateOrganizationRequest) {

    OrganizationEntity organizationEntity = findOrganization();
    OrganizationEntity organizationUpdate = updateValues(
        updateOrganizationRequest,organizationEntity);
    organizationRepository.save(organizationUpdate);

    return organizationMapper.toOrganizationResponseFull(organizationUpdate);
  }

  private OrganizationEntity findOrganization() {
    List<OrganizationEntity> organizationEntities = organizationRepository.findAll();
    if (organizationEntities.isEmpty()) {
      throw new EntityNotFoundException("Missing record in organization table.");
    }
    return organizationEntities.get(0);
  }

  private OrganizationEntity updateValues(UpdateOrganizationRequest updateOrganizationRequest,
      OrganizationEntity organizationEntity) {

    final String name = updateOrganizationRequest.getName();
    final String image = updateOrganizationRequest.getImage();
    final String email = updateOrganizationRequest.getEmail();
    final String welcomeText = updateOrganizationRequest.getWelcomeText();
    final String phone = updateOrganizationRequest.getPhone();
    final String address = updateOrganizationRequest.getAddress();
    final String aboutUsText = updateOrganizationRequest.getAboutUsText();
    final SocialMediaRequest socialMedia = updateOrganizationRequest.getSocialMedia();

    if (name != null) {
      organizationEntity.setName(name);
    }
    if (image != null) {
      organizationEntity.setImage(image);
    }
    if (email != null) {
      organizationEntity.setEmail(email);
    }
    if (welcomeText != null) {
      organizationEntity.setWelcomeText(welcomeText);
    }
    if (phone != null) {
      organizationEntity.setPhone(phone);
    }
    if (address != null) {
      organizationEntity.setAddress(address);
    }
    if (aboutUsText != null) {
      organizationEntity.setAboutUsText(aboutUsText);
    }
    if (socialMedia != null) {

      String facebookUrl = updateOrganizationRequest.getSocialMedia().getFacebookUrl();
      String instagramUrl = updateOrganizationRequest.getSocialMedia().getInstagramUrl();
      String linkedInUrl = updateOrganizationRequest.getSocialMedia().getLinkedInUrl();

      if (facebookUrl != null) {
        organizationEntity.setFacebookUrl(facebookUrl);
      }
      if (instagramUrl != null) {
        organizationEntity.setInstagramUrl(instagramUrl);
      }
      if (linkedInUrl != null) {
        organizationEntity.setLinkedInUrl(linkedInUrl);
      }
    }

    return organizationEntity;
  }

}
