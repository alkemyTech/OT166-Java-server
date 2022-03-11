package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.response.ListFooResponse;
import com.alkemy.ong.application.service.abstraction.IListFooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooResource {

  @Autowired
  private IListFooService listFooService;


  @GetMapping(value = "/foo", produces = {"application/json"})
  public ResponseEntity<ListFooResponse> listFooDetails() {
    return ResponseEntity.ok().body(listFooService.findAll());
  }

}
