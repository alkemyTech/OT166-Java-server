package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.service.abstraction.IDeleteTestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testimonials")
public class TestimonialResource {

  @Autowired
  private IDeleteTestimonialService deleteTestimonialService;

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteTestimonialService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
