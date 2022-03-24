package com.alkemy.ong.application.service;

import com.alkemy.ong.application.rest.request.NewsRequest;
import com.alkemy.ong.application.rest.response.NewsResponse;
import com.alkemy.ong.application.service.abstraction.INewsService;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.INewsMapper;
import com.alkemy.ong.infrastructure.database.repository.ICategoryRepository;
import com.alkemy.ong.infrastructure.database.repository.INewsRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService implements INewsService {

  @Autowired
  private INewsRepository newsRepository;
  @Autowired
  private ICategoryRepository categoryRepository;
  @Autowired
  private INewsMapper newsMapper;

  @Override
  public NewsResponse createNews(NewsRequest newsRequest) {
    Optional<CategoryEntity> categoryNews = categoryRepository.findByName("news");
    CategoryEntity category = new CategoryEntity();
    if (categoryNews.isPresent()) {
      category = categoryNews.get();
    } else {
      category.setName("news");
      categoryRepository.save(category);
    }

    NewsEntity newsEntity = newsMapper.toNewsEntity(newsRequest);
    newsEntity.setCategory(category);
    NewsResponse newsResponse = newsMapper.toNewsResponse(newsRepository.save(newsEntity));

    return newsResponse;
  }

}
