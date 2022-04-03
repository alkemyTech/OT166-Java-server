package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.UpdateUserRequest;
import com.alkemy.ong.application.rest.response.ListUsersResponse;
import com.alkemy.ong.application.service.abstraction.IDeleteUserService;
import com.alkemy.ong.application.service.abstraction.IGetUserService;
import com.alkemy.ong.application.service.abstraction.IUpdateUserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserResource {

  @Autowired
  private IDeleteUserService deleteUserService;
  @Autowired
  private IGetUserService getUserService;

  @Autowired
  private IUpdateUserService updateUserService;

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteUserService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListUsersResponse> listActiveUsers() {
    return ResponseEntity.ok().body(getUserService.listActiveUsers());
  }

  @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> update(@PathVariable Long id,
      @Valid @RequestBody UpdateUserRequest updateUserRequest) {
    updateUserService.update(id, updateUserRequest);
    return ResponseEntity.noContent().build();
  }

}
