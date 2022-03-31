package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.CreateTestimonialRequest;
import com.alkemy.ong.application.rest.response.CreateTestimonialResponse;

public interface ICreateTestimonialService {

  CreateTestimonialResponse create(CreateTestimonialRequest createTestimonialRequest);

}
