package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateCommentRequest;
import com.alkemy.ong.application.rest.request.UpdateCommentRequest;
import com.alkemy.ong.application.rest.response.CommentResponse;
import com.alkemy.ong.application.service.abstraction.ICreateCommentService;
import com.alkemy.ong.application.service.abstraction.IDeleteCommentService;
import com.alkemy.ong.application.service.abstraction.IUpdateCommentService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("comments")
public class CommentResource {

  @Autowired
  private IDeleteCommentService deleteCommentService;

  @Autowired
  private ICreateCommentService createCommentService;

  @Autowired
  private IUpdateCommentService updateCommentService;

  @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteCommentService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<CommentResponse> create(
      @Valid @RequestBody CreateCommentRequest createCommentRequest) {

    CommentResponse commentResponse = createCommentService.save(createCommentRequest);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(commentResponse.getId())
        .toUri();

    return ResponseEntity.created(location).body(commentResponse);
  }

  @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<CommentResponse> update(@PathVariable Long id,
      @RequestBody UpdateCommentRequest updateCommentRequest) {
    return ResponseEntity.ok().body(updateCommentService.update(id, updateCommentRequest));
  }


}
