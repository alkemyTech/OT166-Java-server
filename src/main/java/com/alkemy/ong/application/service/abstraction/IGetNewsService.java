package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.ListNewsResponse;
import com.alkemy.ong.application.rest.response.NewsResponse;
import org.springframework.data.domain.Pageable;

public interface IGetNewsService {

  ListNewsResponse findAll(Pageable pageable);

  NewsResponse getBy(Long id);

}
