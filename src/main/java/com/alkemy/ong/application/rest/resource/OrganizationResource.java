package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.UpdateOrganizationRequest;
import com.alkemy.ong.application.rest.response.OrganizationResponse;
import com.alkemy.ong.application.service.abstraction.IGetOrganizationDetailsService;
import com.alkemy.ong.application.service.abstraction.IUpdateOrganizationService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("organization")
public class OrganizationResource {

  @Autowired
  private IGetOrganizationDetailsService organizationService;

  @Autowired
  private IUpdateOrganizationService updateOrganizationService;

  @GetMapping(value = "/public", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrganizationResponse> getPublicOrganizationDetails() {
    return ResponseEntity.ok().body(organizationService.getPublicOrganizationDetails());
  }

  @PatchMapping(value = "/public", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrganizationResponse> update(@Valid @RequestBody
      UpdateOrganizationRequest updateOrganizationRequest) {
    return ResponseEntity.ok().body(updateOrganizationService.update(updateOrganizationRequest));
  }

}
