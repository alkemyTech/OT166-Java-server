package com.alkemy.ong.application.service;

import com.alkemy.ong.application.rest.request.CreateActivityRequest;
import com.alkemy.ong.application.rest.response.ActivityResponse;
import com.alkemy.ong.application.service.abstraction.ICreateActivityService;
import com.alkemy.ong.infrastructure.database.entity.ActivityEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IActivityMapper;
import com.alkemy.ong.infrastructure.database.repository.IActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActivityService implements ICreateActivityService {

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

}
