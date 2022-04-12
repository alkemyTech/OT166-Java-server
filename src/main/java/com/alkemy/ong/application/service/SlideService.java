package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.request.CreateSlideRequest;
import com.alkemy.ong.application.rest.response.ListSlidesResponse;
import com.alkemy.ong.application.rest.response.SlideResponse;
import com.alkemy.ong.application.service.abstraction.ICreateSlideService;
import com.alkemy.ong.application.service.abstraction.IDeleteSlideService;
import com.alkemy.ong.application.service.abstraction.IGetSlideService;
import com.alkemy.ong.application.util.image.Image;
import com.alkemy.ong.application.util.image.UploadImageDelegate;
import com.alkemy.ong.infrastructure.database.entity.SlideEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.ISlideMapper;
import com.alkemy.ong.infrastructure.database.repository.ISlideRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlideService implements IDeleteSlideService, IGetSlideService, ICreateSlideService {

  @Autowired
  private ISlideRepository slideRepository;

  @Autowired
  private ISlideMapper slideMapper;

  @Autowired
  private UploadImageDelegate uploadImageDelegate;

  @Override
  public void delete(Long id) {
    checkIfExist(id);
    slideRepository.deleteById(id);
  }

  @Override
  public List<SlideResponse> list() {
    List<SlideEntity> slideEntities = slideRepository.findAllByOrderByOrder();
    return slideMapper.toListSlideResponse(slideEntities);
  }

  @Override
  public SlideResponse getBy(Long id) {
    checkIfExist(id);
    return slideMapper.toSlideResponse(slideRepository.getById(id));
  }

  @Override
  public ListSlidesResponse listWithLessProperties() {
    List<SlideEntity> slideEntities = slideRepository.findAll();
    ListSlidesResponse listSlidesResponse = new ListSlidesResponse();
    listSlidesResponse.setSlides(slideMapper.toSlideImageAndOrderResponse(slideEntities));
    return listSlidesResponse;
  }

  @Override
  public SlideResponse create(CreateSlideRequest createSlideRequest) {
    String imageUrl = uploadImage(createSlideRequest);
    SlideEntity slideEntity = buildSlide(imageUrl, createSlideRequest);
    return slideMapper.toSlideResponse(slideRepository.save(slideEntity));
  }

  private void checkIfExist(Long id) {
    if (!slideRepository.existsById(id)) {
      throw new EntityNotFoundException("Slide not found.");
    }
  }

  private String uploadImage(CreateSlideRequest slideRequest) {
    return uploadImageDelegate.upload(Image.buildImage(slideRequest));
  }

  private SlideEntity buildSlide(String imageUrl, CreateSlideRequest slideRequest) {
    return SlideEntity.builder()
        .imageUrl(imageUrl)
        .text(slideRequest.getFileName())
        .order(determineOrder(slideRequest.getOrder()))
        .build();
  }

  private int determineOrder(Integer order) {
    return (order == null || order <= 0) ? slideRepository.getHighestOrder() + 1 : order;
  }

}