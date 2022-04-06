package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.ListUsersResponse;
import com.alkemy.ong.application.rest.response.UserResponse;

public interface IGetUserService {

  ListUsersResponse listActiveUsers();

  UserResponse getUserAuthenticated();

}