package com.alkemy.ong.infrastructure.database.mapper;

import com.alkemy.ong.application.rest.response.FooResponse;
import com.alkemy.ong.application.rest.response.ListFooResponse;
import com.alkemy.ong.infrastructure.database.entity.FooEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FooMapper {

  public ListFooResponse map(List<FooEntity> fooEntities) {
    if (fooEntities == null || fooEntities.isEmpty()) {
      return new ListFooResponse(Collections.emptyList());
    }

    List<FooResponse> fooResponses = new ArrayList<>(fooEntities.size());
    for (FooEntity fooEntity : fooEntities) {
      fooResponses.add(new FooResponse(fooEntity.getName()));
    }

    return new ListFooResponse(fooResponses);
  }
}
