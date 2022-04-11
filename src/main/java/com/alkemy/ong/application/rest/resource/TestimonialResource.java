package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateTestimonialRequest;
import com.alkemy.ong.application.rest.request.UpdateTestimonialRequest;
import com.alkemy.ong.application.rest.response.TestimonialResponse;
import com.alkemy.ong.application.service.abstraction.ICreateTestimonialService;
import com.alkemy.ong.application.service.abstraction.IDeleteTestimonialService;
import com.alkemy.ong.application.service.abstraction.IUpdateTestimonialService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/testimonials")
public class TestimonialResource {

  @Autowired
  private IDeleteTestimonialService deleteTestimonialService;

  @Autowired
  private ICreateTestimonialService createTestimonialService;

  @Autowired
  private IUpdateTestimonialService updateTestimonialService;

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteTestimonialService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TestimonialResponse> create(
      @Valid @RequestBody CreateTestimonialRequest createTestimonialRequest) {

    TestimonialResponse testimonialResponse = createTestimonialService.create(
        createTestimonialRequest);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(testimonialResponse.getId())
        .toUri();

    return ResponseEntity.created(location).body(testimonialResponse);

  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TestimonialResponse> update(@PathVariable Long id,
      @Valid @RequestBody UpdateTestimonialRequest updateTestimonialRequest) {
    return ResponseEntity.ok().body(updateTestimonialService.update(id, updateTestimonialRequest));
  }
}
