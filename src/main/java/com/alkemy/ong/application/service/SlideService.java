package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFound;
import com.alkemy.ong.application.service.abstraction.IDeleteSlide;
import com.alkemy.ong.infrastructure.database.repository.ISlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlideService implements IDeleteSlide {

  @Autowired
  private ISlideRepository repository;

  @Override
  public void delete(Long id) {
    if (repository.findById(id).isEmpty()) {
      throw new EntityNotFound("Slide not found.");
    }
    repository.deleteById(id);
  }
}
