package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.request.CreateTestimonialRequest;
import com.alkemy.ong.application.rest.request.UpdateTestimonialRequest;
import com.alkemy.ong.application.rest.response.ListTestimonialsResponse;
import com.alkemy.ong.application.rest.response.TestimonialResponse;
import com.alkemy.ong.application.service.abstraction.ICreateTestimonialService;
import com.alkemy.ong.application.service.abstraction.IDeleteTestimonialService;
import com.alkemy.ong.application.service.abstraction.IGetTestimonialService;
import com.alkemy.ong.application.service.abstraction.IUpdateTestimonialService;
import com.alkemy.ong.infrastructure.database.entity.TestimonialEntity;
import com.alkemy.ong.infrastructure.database.mapper.ITestimonialMapper;
import com.alkemy.ong.infrastructure.database.repository.ITestimonialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TestimonialService implements IDeleteTestimonialService,
    ICreateTestimonialService, IUpdateTestimonialService, IGetTestimonialService {

  @Autowired
  private ITestimonialRepository testimonialRepository;

  @Autowired
  private ITestimonialMapper testimonialMapper;

  @Override
  public void delete(Long id) {
    TestimonialEntity testimonialEntity = findBy(id);
    testimonialEntity.setSoftDelete(true);
    testimonialRepository.save(testimonialEntity);
  }

  private TestimonialEntity findBy(Long id) {
    Optional<TestimonialEntity> result = testimonialRepository.findById(id);
    if (result.isEmpty() || Boolean.TRUE.equals(result.get().getSoftDelete())) {
      throw new EntityNotFoundException("Testimonial not found.");
    }
    return result.get();
  }

  @Override
  public TestimonialResponse create(CreateTestimonialRequest createTestimonialRequest) {
    TestimonialEntity testimonialEntity = testimonialMapper.toTestimonialEntity(
        createTestimonialRequest);
    testimonialEntity = testimonialRepository.save(testimonialEntity);
    return testimonialMapper.toTestimonialResponse(testimonialEntity);
  }

  @Override
  public TestimonialResponse update(Long id, UpdateTestimonialRequest updateTestimonialRequest) {
    TestimonialEntity testimonialUpdate = findBy(id);
    testimonialUpdate.setName(updateTestimonialRequest.getName());
    testimonialUpdate.setContent(updateTestimonialRequest.getContent());
    testimonialUpdate.setImage(updateTestimonialRequest.getImage());
    return testimonialMapper.toTestimonialResponse(testimonialRepository.save(testimonialUpdate));
  }

  @Override
  public ListTestimonialsResponse list(Pageable pageable) {
    Page<TestimonialEntity> page = testimonialRepository.findByOrderByIdAsc(pageable);
    ListTestimonialsResponse listTestimonials = buildListTestimonialsResponse(page.getContent());
    setPagination(listTestimonials, page);
    return listTestimonials;
  }

  private ListTestimonialsResponse buildListTestimonialsResponse(
      List<TestimonialEntity> testimonialEntities) {
    return ListTestimonialsResponse.builder()
        .testimonials(testimonialMapper.toListTestimonialResponse(testimonialEntities))
        .build();
  }

  private void setPagination(ListTestimonialsResponse listTestimonialsResponse,
      Page<TestimonialEntity> page) {
    listTestimonialsResponse.setPage(page.getNumber());
    listTestimonialsResponse.setTotalPages(page.getTotalPages());
    listTestimonialsResponse.setSize(page.getSize());
  }

}
