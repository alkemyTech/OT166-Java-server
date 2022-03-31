package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateActivityRequest;
import com.alkemy.ong.application.rest.request.UpdateActivityRequest;
import com.alkemy.ong.application.rest.response.ActivityResponse;
import com.alkemy.ong.application.service.abstraction.ICreateActivityService;
import com.alkemy.ong.application.service.abstraction.IUpdateActivityService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("activities")
public class ActivityResource {

  @Autowired
  private ICreateActivityService createActivityService;

  @Autowired
  private IUpdateActivityService updateActivityService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ActivityResponse> create(
      @Valid @RequestBody CreateActivityRequest createActivityRequest) {

    ActivityResponse activityResponse = createActivityService.save(createActivityRequest);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(activityResponse.getId())
        .toUri();

    return ResponseEntity.created(location).body(activityResponse);

  }

  @PutMapping("/{id}")
  public ResponseEntity<ActivityResponse> update(@PathVariable long id,
      @Valid @RequestBody UpdateActivityRequest updateActivityRequest) {
    return ResponseEntity.ok(updateActivityService.update(id, updateActivityRequest));
  }

}
