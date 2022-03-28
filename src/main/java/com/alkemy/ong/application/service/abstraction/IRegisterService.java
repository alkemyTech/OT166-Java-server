package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.RegisterRequest;
import com.alkemy.ong.application.rest.response.RegisterResponse;

public interface IRegisterService {

  RegisterResponse register(RegisterRequest registerRequest);

}
