package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.UpdateUserRequest;
import com.alkemy.ong.application.rest.response.UpdatedUserResponse;

public interface IUpdateUserService {

  UpdatedUserResponse update(Long id, UpdateUserRequest updateUserRequest);

}
