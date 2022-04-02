package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.UserResponse;

public interface IGetUserAuthenticateService {

  UserResponse getUserAuthenticated(String authorization);

}
