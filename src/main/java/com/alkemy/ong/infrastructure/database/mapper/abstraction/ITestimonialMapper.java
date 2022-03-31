package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.request.CreateTestimonialRequest;
import com.alkemy.ong.application.rest.response.CreateTestimonialResponse;
import com.alkemy.ong.application.rest.response.OrganizationResponse;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import com.alkemy.ong.infrastructure.database.entity.TestimonialEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITestimonialMapper {

  TestimonialEntity toTestimonialEntity(CreateTestimonialRequest createTestimonialRequest);

  CreateTestimonialResponse toCreateTestimonialResponse(TestimonialEntity testimonialEntity);

}
