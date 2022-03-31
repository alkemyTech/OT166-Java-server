package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.response.SlideResponse;
import com.alkemy.ong.application.service.abstraction.IDeleteSlideService;
import com.alkemy.ong.application.service.abstraction.IGetSlideService;
import com.alkemy.ong.infrastructure.database.entity.SlideEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.ISlideMapper;
import com.alkemy.ong.infrastructure.database.repository.ISlideRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlideService implements IDeleteSlideService, IGetSlideService {

  @Autowired
  private ISlideRepository slideRepository;
  
  @Autowired
  private ISlideMapper slideMapper;

  @Override
  public void delete(Long id) {
    if (!slideRepository.existsById(id)) {
      throw new EntityNotFoundException("Slide not found.");
    }
    slideRepository.deleteById(id);
  }

  @Override
  public List<SlideResponse> listSlides() {
    List<SlideEntity> slideEntities = slideRepository.findAllByOrderByOrder();
    if (slideEntities.isEmpty()) {
      throw new EntityNotFoundException("Missing record in slide table.");
    }
    
    return slideMapper.toListSlideResponse(slideEntities);
  }
}
