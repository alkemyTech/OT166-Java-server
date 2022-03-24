package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.NewsRequest;
import com.alkemy.ong.application.rest.response.NewsResponse;

public interface INewsService {
  
  NewsResponse createNews(NewsRequest newsRequest);
  
}
