package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.UserResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface IGetListUserService {

  List<UserResponse> getList();
}
