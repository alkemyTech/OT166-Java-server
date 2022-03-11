package com.alkemy.ong.application.service;

import com.alkemy.ong.application.rest.response.ListFooResponse;
import com.alkemy.ong.application.service.abstraction.IListFooService;
import com.alkemy.ong.infrastructure.database.mapper.FooMapper;
import com.alkemy.ong.infrastructure.database.repository.IFooRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FooService implements IListFooService {

  private final IFooRepository fooRepository;
  private final FooMapper fooMapper;

  @Override
  public ListFooResponse findAll() {
    return fooMapper.map(fooRepository.findAll());
  }
}
