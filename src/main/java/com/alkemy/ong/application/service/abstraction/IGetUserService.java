package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.ListUsersResponse;

public interface IGetUserService {

  ListUsersResponse listActiveUsers();

}
