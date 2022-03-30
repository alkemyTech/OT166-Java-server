package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.ListUserResponse;
import org.springframework.stereotype.Service;

@Service
public interface IGetListUserService {

  ListUserResponse getList();
}
