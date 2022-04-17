package com.alkemy.ong.application.rest.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ListTestimonialsResponse extends PaginationResponse {

  private List<TestimonialResponse> testimonials;

}
