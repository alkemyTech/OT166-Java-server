package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateContactRequest;
import com.alkemy.ong.application.rest.response.ContactResponse;
import com.alkemy.ong.application.service.abstraction.ICreateContactService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/contacts")
public class ContactResource {

  @Autowired
  private ICreateContactService createContactService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ContactResponse> create(
      @Valid @RequestBody CreateContactRequest createContactRequest) {
    ContactResponse contactResponse = createContactService.create(createContactRequest);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(contactResponse.getId())
        .toUri();

    return ResponseEntity.created(location).body(contactResponse);
  }

}
