package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.service.abstraction.IDeleteTestimonialService;
import com.alkemy.ong.infrastructure.database.entity.TestimonialEntity;
import com.alkemy.ong.infrastructure.database.repository.ITestimonialRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestimonialService implements IDeleteTestimonialService {

  @Autowired
  private ITestimonialRepository testimonialRepository;

  @Override
  public void delete(Long id) {
    Optional<TestimonialEntity> result = testimonialRepository.findById(id);
    if (result.isEmpty() || Boolean.TRUE.equals(result.get().getSoftDelete())) {
      throw new EntityNotFoundException("Testimonial not found.");
    }

    TestimonialEntity testimonialEntity = result.get();
    testimonialEntity.setSoftDelete(true);
    testimonialRepository.save(testimonialEntity);
  }
}
