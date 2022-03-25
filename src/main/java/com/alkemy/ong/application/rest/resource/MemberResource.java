package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateMemberRequest;
import com.alkemy.ong.application.rest.response.MemberResponse;
import com.alkemy.ong.application.service.abstraction.ICreateMemberService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("members")
public class MemberResource {

  @Autowired
  private ICreateMemberService createMemberService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MemberResponse> create(
      @Valid @RequestBody CreateMemberRequest createMemberRequest) {
    return ResponseEntity.ok().body(createMemberService.save(createMemberRequest));
  }

}
