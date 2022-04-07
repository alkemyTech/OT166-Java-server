package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.UpdateNewsRequest;
import com.alkemy.ong.application.rest.response.NewsResponse;

public interface IUpdateNewsService {

  NewsResponse update(long id, UpdateNewsRequest updateNewsRequest);

}
