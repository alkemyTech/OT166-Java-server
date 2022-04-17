package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.ListTestimonialsResponse;
import org.springframework.data.domain.Pageable;

public interface IGetTestimonialService {

  ListTestimonialsResponse list(Pageable pageable);

}
