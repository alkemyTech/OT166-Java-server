package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateMemberRequest;
import com.alkemy.ong.application.rest.request.UpdateMemberRequest;
import com.alkemy.ong.application.rest.response.ErrorResponse;
import com.alkemy.ong.application.rest.response.ListMembersResponse;
import com.alkemy.ong.application.rest.response.MemberResponse;
import com.alkemy.ong.application.rest.response.RegisterResponse;
import com.alkemy.ong.application.service.abstraction.ICreateMemberService;
import com.alkemy.ong.application.service.abstraction.IDeleteMemberService;
import com.alkemy.ong.application.service.abstraction.IGetMemberService;
import com.alkemy.ong.application.service.abstraction.IUpdateMemberService;
import com.alkemy.ong.application.util.PaginatedResultsRetrieved;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Member Endpoints", description = "Member Endpoints")
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

  @Operation(summary = "Create Member.", description = "Create a new member.", tags = "Post")
  @ApiResponses(value = {
      @ApiResponse(content = @Content(schema = @Schema(implementation = MemberResponse.class)),
          responseCode = "201", description = "Returns member created."),
      @ApiResponse(responseCode = "400", description = "Invalid input data.", content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)
      ))
  })
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MemberResponse> create(
      @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
          description = "New member to create", content = @Content(schema = @Schema(implementation =
          CreateMemberRequest.class))) CreateMemberRequest createMemberRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(createMemberService.save(createMemberRequest));
  }

  @Operation(summary = "Get members.", description = "Get actives members.", tags = "Get")
  @ApiResponse(responseCode = "200", description = "Returns list of members.", content = @Content(
      schema = @Schema(implementation = MemberResponse.class)))
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListMembersResponse> listActiveMembers(Pageable pageable,
      UriComponentsBuilder uriBuilder,
      HttpServletResponse response) {
    ListMembersResponse listMembersResponse = getMemberService.listActiveMembers(pageable);

    paginatedResultsRetrieved.addLinkHeaderOnPagedResourceRetrieval(
        uriBuilder, response, "/members",
        listMembersResponse.getPage(),
        listMembersResponse.getTotalPages(),
        listMembersResponse.getSize());

    return ResponseEntity.ok().body(listMembersResponse);
  }

  @Operation(summary = "Delete member.", description = "Delete member.", tags = "Delete")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Member deleted."),
      @ApiResponse(responseCode = "404", description = "Member not found.", content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteMemberService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Update Member.", description = "Update member.", tags = "Put")
  @ApiResponses(value = {
      @ApiResponse(content = @Content(schema = @Schema(implementation = MemberResponse.class)),
          responseCode = "200", description = "Returns member updated."),
      @ApiResponse(responseCode = "400", description = "Invalid input data.", content = @Content(
          schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Member not found.", content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MemberResponse> update(
      @PathVariable Long id, @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters
      .RequestBody(required = true, description = "Member updated", content = @Content(
          schema = @Schema(implementation = UpdateMemberRequest.class)))
      UpdateMemberRequest updateMemberRequest) {
    return ResponseEntity.ok().body(updateMemberService.update(id, updateMemberRequest));
  }

}
