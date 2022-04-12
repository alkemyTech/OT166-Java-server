package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateSlideRequest;
import com.alkemy.ong.application.rest.response.ListSlidesResponse;
import com.alkemy.ong.application.rest.response.SlideResponse;
import com.alkemy.ong.application.service.abstraction.ICreateSlideService;
import com.alkemy.ong.application.service.abstraction.IDeleteSlideService;
import com.alkemy.ong.application.service.abstraction.IGetSlideService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("slides")
public class SlideResource {

  @Autowired
  private IDeleteSlideService deleteSlideService;

  @Autowired
  private IGetSlideService getSlideService;

  @Autowired
  private ICreateSlideService createSlideService;


  @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteSlideService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SlideResponse> getBy(@PathVariable Long id) {
    return ResponseEntity.ok().body(getSlideService.getBy(id));
  }


  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SlideResponse> create(
      @RequestBody CreateSlideRequest createSlideRequest) {

    SlideResponse slideResponse = createSlideService.create(
        createSlideRequest);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(slideResponse.getId())
        .toUri();

    return ResponseEntity.created(location).body(slideResponse);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListSlidesResponse> list() {
    return ResponseEntity.ok().body(getSlideService.listWithLessProperties());
  }

}
