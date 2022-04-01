package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.request.CreateActivityRequest;
import com.alkemy.ong.application.rest.request.UpdateActivityRequest;
import com.alkemy.ong.application.rest.response.ActivityResponse;
import com.alkemy.ong.application.service.abstraction.ICreateActivityService;
import com.alkemy.ong.application.service.abstraction.IUpdateActivityService;
import com.alkemy.ong.infrastructure.database.entity.ActivityEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IActivityMapper;
import com.alkemy.ong.infrastructure.database.repository.IActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActivityService implements ICreateActivityService, IUpdateActivityService {

  @Autowired
  public IActivityRepository activityRepository;

  @Autowired
  public IActivityMapper activityMapper;

  @Override
  public ActivityResponse save(CreateActivityRequest createActivityRequest) {
    ActivityEntity activityEntity = activityMapper.toActivityEntity(createActivityRequest);
    activityEntity.setSoftDeleted(false);
    return activityMapper.toActivityResponse(activityRepository.save(activityEntity));
  }

  @Override
  public ActivityResponse update(long id, UpdateActivityRequest updateActivityRequest) {
    ActivityEntity activityEntity = findBy(id);
    activityEntity.setName(updateActivityRequest.getName());
    activityEntity.setContent(updateActivityRequest.getContent());
    activityEntity.setImage(updateActivityRequest.getImage());
    return activityMapper.toActivityResponse(activityEntity);
  }

  private ActivityEntity findBy(long id) {
    ActivityEntity activityEntity = activityRepository.findByIdAndSoftDeletedFalse(id);
    if (activityEntity == null) {
      throw new EntityNotFoundException("Activity not found.");
    }
    return activityEntity;
  }
}
