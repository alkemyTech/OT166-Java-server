package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.exception.EntityNotFound;
import com.alkemy.ong.application.service.abstraction.IDeleteSlide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "slides")
public class SlideResource {

  @Autowired
  private IDeleteSlide service;

  // ^\d+$ -> positive integers (regular expressions)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(path = "/{id:^\\d+$}")
  public void delete(@PathVariable Long id) throws EntityNotFound {
    service.delete(id);
  }

}
