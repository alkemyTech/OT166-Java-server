package com.alkemy.ong.infrastructure.spring.config.seeder;

import com.alkemy.ong.infrastructure.database.entity.ActivityEntity;
import com.alkemy.ong.infrastructure.database.repository.IActivityRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ActivitySeeder {

  private static final List<String> ACTIVITIES_NAMES = List.of(
      "Elementary School school-based support programmes",
      "High School school-based support programmes", "Lessons");
  private static final List<String> ACTIVITIES_CONTENTS = List.of("The school support space is the "
      + "heart of the educational area.This workshop is designed to help students with the "
      + "material they bring from school, we also have a teacher who gives them "
      + "language and math classes.", "Here the young people present themselves with the "
      + "material they bring from school and a teacher from the institution and a group of "
      + "volunteers welcomes them to help them study or do homework.", "It is a program aimed at "
      + "young people from the third year of high school, "
      + "whose objective is to guarantee their permanence in school and build a "
      + "life project that gives meaning to the school.");
  private static final String ACTIVITY_IMAGE = "https://aws.amazon.com/activity/activity-image.img";

  @Autowired
  private IActivityRepository activityRepository;

  @EventListener
  public void seed(ContextRefreshedEvent event) {
    createActivities();
  }

  private void createActivities() {
    if (activityRepository.count() == 0) {
      saveActivities();
    }
  }

  private void saveActivities() {
    List<ActivityEntity> activityEntities = new ArrayList<>(3);
    for (int index = 0; index < 3; index++) {
      activityEntities.add(buildActivity(
          ACTIVITIES_NAMES.get(index),
          ACTIVITIES_CONTENTS.get(index)));
    }
    activityRepository.saveAll(activityEntities);
  }

  private ActivityEntity buildActivity(String name, String content) {
    return ActivityEntity.builder()
        .name(name)
        .content(content)
        .image(ACTIVITY_IMAGE)
        .softDeleted(false)
        .build();
  }
}
