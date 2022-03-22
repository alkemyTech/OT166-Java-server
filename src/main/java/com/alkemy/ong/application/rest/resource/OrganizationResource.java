package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.exception.EntityNotFound;
import com.alkemy.ong.application.rest.response.OrganizationResponse;
import com.alkemy.ong.application.service.abstraction.IGetOrganizationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("organization")
public class OrganizationResource {
  @Autowired
  private IGetOrganizationDetailsService organizationService;

  @GetMapping("/public")
  public ResponseEntity<OrganizationResponse> getOrganizationPublicDetails() {
    return ResponseEntity.ok().body(organizationService.getPublicOrganization());
  }

}
