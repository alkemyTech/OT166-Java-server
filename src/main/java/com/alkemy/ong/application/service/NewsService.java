package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.request.CreateNewsRequest;
import com.alkemy.ong.application.rest.request.UpdateNewsRequest;
import com.alkemy.ong.application.rest.response.NewsResponse;
import com.alkemy.ong.application.service.abstraction.ICreateNewsService;
import com.alkemy.ong.application.service.abstraction.IDeleteNewsService;
import com.alkemy.ong.application.service.abstraction.IUpdateNewsService;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.INewsMapper;
import com.alkemy.ong.infrastructure.database.repository.ICategoryRepository;
import com.alkemy.ong.infrastructure.database.repository.INewsRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService implements ICreateNewsService, IDeleteNewsService, IUpdateNewsService {

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

  @Override
  public NewsResponse update(Long id, UpdateNewsRequest updateNewsRequest) {
    NewsEntity newsEntity = findBy(id);
    newsEntity.setName(updateNewsRequest.getName());
    newsEntity.setContent(updateNewsRequest.getText());
    newsEntity.setImage(updateNewsRequest.getImage());
    return newsMapper.toNewsResponse(newsRepository.save(newsEntity));
  }

  @Override
  public void delete(Long id) {
    NewsEntity newsEntity = findBy(id);
    newsEntity.setSoftDeleted(true);
    newsRepository.save(newsEntity);
  }

  private NewsEntity findBy(Long id) {
    Optional<NewsEntity> optionalNewsEntity = newsRepository.findById(id);
    if (optionalNewsEntity.isEmpty()
        || Boolean.TRUE.equals(optionalNewsEntity.get().getSoftDeleted())) {
      throw new EntityNotFoundException("News not found.");
    }
    return optionalNewsEntity.get();
  }

}
