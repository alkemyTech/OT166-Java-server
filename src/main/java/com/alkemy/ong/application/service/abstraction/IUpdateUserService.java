package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.UpdateUserRequest;

public interface IUpdateUserService {

  void update(Long id, UpdateUserRequest updateUserRequest);

}
