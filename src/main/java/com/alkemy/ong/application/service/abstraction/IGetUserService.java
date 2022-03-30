package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.ListUsersResponse;
import org.springframework.stereotype.Service;

@Service
public interface IGetUserService {

  ListUsersResponse listActiveUsers();
}
