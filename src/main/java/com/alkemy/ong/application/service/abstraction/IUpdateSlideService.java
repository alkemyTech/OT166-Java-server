package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.UpdateSlideRequest;
import com.alkemy.ong.application.rest.response.SlideResponse;

public interface IUpdateSlideService {

  SlideResponse update(Long id, UpdateSlideRequest updateSlideRequest);

}
