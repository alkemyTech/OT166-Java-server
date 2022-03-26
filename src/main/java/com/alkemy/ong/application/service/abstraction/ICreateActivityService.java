package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.CreateActivityRequest;
import com.alkemy.ong.application.rest.request.CreateMemberRequest;
import com.alkemy.ong.application.rest.response.ActivityResponse;

public interface ICreateActivityService {

  ActivityResponse save(CreateActivityRequest activity);

}
