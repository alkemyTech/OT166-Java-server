package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.UpdateTestimonialRequest;
import com.alkemy.ong.application.rest.response.TestimonialResponse;

public interface IUpdateTestimonialService {

  TestimonialResponse update(Long id, UpdateTestimonialRequest updateTestimonialRequest);

}
