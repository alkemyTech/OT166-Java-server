package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.service.abstraction.IDeleteSlideService;
import com.alkemy.ong.infrastructure.database.repository.ISlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlideService implements IDeleteSlideService {

  @Autowired
  private ISlideRepository slideRepository;

  @Override
  public void delete(Long id) {
    if (!slideRepository.existsById(id)) {
      throw new EntityNotFoundException("Slide not found.");
    }
    slideRepository.deleteById(id);
  }
}
