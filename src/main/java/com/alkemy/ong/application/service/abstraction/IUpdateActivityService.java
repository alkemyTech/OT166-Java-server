package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.UpdateActivityRequest;
import com.alkemy.ong.application.rest.response.ActivityResponse;

public interface IUpdateActivityService {

  ActivityResponse update(long id, UpdateActivityRequest updateActivityRequest);

}
