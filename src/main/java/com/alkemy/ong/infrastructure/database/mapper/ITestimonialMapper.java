package com.alkemy.ong.infrastructure.database.mapper;

import com.alkemy.ong.application.rest.request.CreateTestimonialRequest;
import com.alkemy.ong.application.rest.response.TestimonialResponse;
import com.alkemy.ong.infrastructure.database.entity.TestimonialEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITestimonialMapper {

  TestimonialEntity toTestimonialEntity(CreateTestimonialRequest createTestimonialRequest);

  TestimonialResponse toTestimonialResponse(TestimonialEntity testimonialEntity);

}
