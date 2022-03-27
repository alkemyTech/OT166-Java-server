package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.request.CreateNewsRequest;
import com.alkemy.ong.application.rest.response.NewsResponse;
import com.alkemy.ong.application.service.abstraction.ICreateNewsService;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.INewsMapper;
import com.alkemy.ong.infrastructure.database.repository.ICategoryRepository;
import com.alkemy.ong.infrastructure.database.repository.INewsRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService implements ICreateNewsService {

  @Autowired
  private INewsRepository newsRepository;
  @Autowired
  private ICategoryRepository categoryRepository;
  @Autowired
  private INewsMapper newsMapper;

  @Override
  public NewsResponse create(CreateNewsRequest newsRequest) {
    Optional<CategoryEntity> categoryNews = categoryRepository.findByName("news");
    if (categoryNews.isEmpty()) {
      throw new EntityNotFoundException("Missing record in category table.");
    }

    NewsEntity newsEntity = newsMapper.toNewsEntity(newsRequest);
    newsEntity.setCategory(categoryNews.get());
    newsEntity.setSoftDeleted(false);
    return newsMapper.toNewsResponse(newsRepository.save(newsEntity));
  }

}
