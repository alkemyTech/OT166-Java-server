package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.CreateSlideRequest;
import com.alkemy.ong.application.rest.response.SlideResponse;

public interface ICreateSlideService {

  SlideResponse create(CreateSlideRequest createSlideRequest);

}
