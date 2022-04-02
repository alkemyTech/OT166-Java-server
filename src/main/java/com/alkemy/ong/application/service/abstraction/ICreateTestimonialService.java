package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.CreateTestimonialRequest;
import com.alkemy.ong.application.rest.response.TestimonialResponse;

public interface ICreateTestimonialService {

  TestimonialResponse create(CreateTestimonialRequest createTestimonialRequest);

}
