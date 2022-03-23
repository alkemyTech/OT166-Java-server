package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.MemberRequest;
import com.alkemy.ong.application.rest.response.MemberResponse;
import com.alkemy.ong.application.service.abstraction.ISaveMember;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("members")
public class MemberResource {

  @Autowired
  private ISaveMember memberService;

  @PostMapping
  public ResponseEntity<MemberResponse> save(@Valid @RequestBody MemberRequest member) {
    return ResponseEntity.ok().body(memberService.save(member));
  }

}
