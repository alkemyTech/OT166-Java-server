package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.service.abstraction.IDeleteSlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("slides")
public class SlideResource {

  @Autowired
  private IDeleteSlideService deleteSlideService;

  @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteSlideService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
