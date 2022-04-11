package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateMemberRequest;
import com.alkemy.ong.application.rest.request.UpdateMemberRequest;
import com.alkemy.ong.application.rest.response.ListMembersResponse;
import com.alkemy.ong.application.rest.response.MemberResponse;
import com.alkemy.ong.application.service.abstraction.ICreateMemberService;
import com.alkemy.ong.application.service.abstraction.IDeleteMemberService;
import com.alkemy.ong.application.service.abstraction.IGetMemberService;
import com.alkemy.ong.application.service.abstraction.IUpdateMemberService;
import com.alkemy.ong.application.util.PaginatedResultsRetrieved;
import com.alkemy.ong.infrastructure.database.entity.MemberEntity;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("members")
public class MemberResource {

  @Autowired
  private ICreateMemberService createMemberService;

  @Autowired
  private IGetMemberService getMemberService;

  @Autowired
  private IDeleteMemberService deleteMemberService;

  @Autowired
  private IUpdateMemberService updateMemberService;

  @Autowired
  private PaginatedResultsRetrieved paginatedResultsRetrieved;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MemberResponse> create(
      @Valid @RequestBody CreateMemberRequest createMemberRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(createMemberService.save(createMemberRequest));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListMembersResponse> listActiveMembers(Pageable pageable,
      UriComponentsBuilder uriBuilder,
      HttpServletResponse response) {
    ListMembersResponse listMembersResponse = getMemberService.listActiveMembers(pageable);

    paginatedResultsRetrieved.addLinkHeaderOnPagedResourceRetrieval(
        uriBuilder, response,"/members",
        listMembersResponse.getPage(),
        listMembersResponse.getTotalPages(),
        listMembersResponse.getSize());

    return ResponseEntity.ok().body(listMembersResponse);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteMemberService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MemberResponse> update(@PathVariable Long id, @Valid @RequestBody
      UpdateMemberRequest updateMemberRequest) {
    return ResponseEntity.ok().body(updateMemberService.update(id, updateMemberRequest));
  }

}
