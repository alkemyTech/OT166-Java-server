package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateActivityRequest;
import com.alkemy.ong.application.rest.response.ActivityResponse;
import com.alkemy.ong.application.service.abstraction.ICreateActivityService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("activities")
public class ActivityResource {

  @Autowired
  private ICreateActivityService createActivityService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ActivityResponse> create(
      @Valid @RequestBody CreateActivityRequest createActivityRequest) {

    return ResponseEntity.ok().body(createActivityService.save(createActivityRequest));

  }

}
