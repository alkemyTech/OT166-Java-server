package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.request.SocialMediaRequest;
import com.alkemy.ong.application.rest.request.UpdateOrganizationRequest;
import com.alkemy.ong.application.rest.response.OrganizationResponse;
import com.alkemy.ong.application.service.abstraction.IGetOrganizationDetailsService;
import com.alkemy.ong.application.service.abstraction.IGetSlideService;
import com.alkemy.ong.application.service.abstraction.IUpdateOrganizationService;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import com.alkemy.ong.infrastructure.database.mapper.IOrganizationMapper;
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
    OrganizationResponse organizationResponse =
        organizationMapper.toOrganizationResponse(findOrganization());
    organizationResponse.setSlides(getSlideService.list());
    return organizationResponse;
  }

  @Override
  public OrganizationResponse update(UpdateOrganizationRequest updateOrganizationRequest) {
    OrganizationEntity organizationUpdate =
        updateValues(updateOrganizationRequest, findOrganization());
    organizationRepository.save(organizationUpdate);

    return organizationMapper.toOrganizationResponseUpdate(organizationUpdate);
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

    updateBasicInformation(updateOrganizationRequest, organizationEntity);
    updateOrganizationText(updateOrganizationRequest, organizationEntity);
    updateSocialMedia(organizationEntity, updateOrganizationRequest.getSocialMedia());

    return organizationEntity;
  }

  private void updateBasicInformation(UpdateOrganizationRequest updateOrganizationRequest,
      OrganizationEntity organizationEntity) {
    String name = updateOrganizationRequest.getName();
    if (name != null) {
      organizationEntity.setName(name);
    }

    String image = updateOrganizationRequest.getImage();
    if (image != null) {
      organizationEntity.setImage(image);
    }

    String email = updateOrganizationRequest.getEmail();
    if (email != null) {
      organizationEntity.setEmail(email);
    }

    String phone = updateOrganizationRequest.getPhone();
    if (phone != null) {
      organizationEntity.setPhone(phone);
    }

    String address = updateOrganizationRequest.getAddress();
    if (address != null) {
      organizationEntity.setAddress(address);
    }
  }

  private void updateOrganizationText(UpdateOrganizationRequest updateOrganizationRequest,
      OrganizationEntity organizationEntity) {
    String welcomeText = updateOrganizationRequest.getWelcomeText();
    if (welcomeText != null) {
      organizationEntity.setWelcomeText(welcomeText);
    }

    String aboutUsText = updateOrganizationRequest.getAboutUsText();
    if (aboutUsText != null) {
      organizationEntity.setAboutUsText(aboutUsText);
    }
  }

  private void updateSocialMedia(OrganizationEntity organizationEntity,
      SocialMediaRequest socialMediaRequest) {
    if (socialMediaRequest != null) {

      String facebookUrl = socialMediaRequest.getFacebookUrl();
      if (facebookUrl != null) {
        organizationEntity.setFacebookUrl(facebookUrl);
      }

      String instagramUrl = socialMediaRequest.getInstagramUrl();
      if (instagramUrl != null) {
        organizationEntity.setInstagramUrl(instagramUrl);
      }

      String linkedInUrl = socialMediaRequest.getLinkedInUrl();
      if (linkedInUrl != null) {
        organizationEntity.setLinkedInUrl(linkedInUrl);
      }
    }
  }

}
