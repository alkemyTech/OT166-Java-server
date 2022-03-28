package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.CreateNewsRequest;
import com.alkemy.ong.application.rest.response.NewsResponse;

public interface ICreateNewsService {

  NewsResponse create(CreateNewsRequest newsRequest);

}
