package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.request.CreateNewsRequest;
import com.alkemy.ong.application.rest.request.UpdateNewsRequest;
import com.alkemy.ong.application.rest.response.CommentResponse;
import com.alkemy.ong.application.rest.response.ListCommentsResponse;
import com.alkemy.ong.application.rest.response.ListNewsResponse;
import com.alkemy.ong.application.rest.response.NewsResponse;
import com.alkemy.ong.application.service.abstraction.ICreateNewsService;
import com.alkemy.ong.application.service.abstraction.IDeleteNewsService;
import com.alkemy.ong.application.service.abstraction.IGetNewsService;
import com.alkemy.ong.application.service.abstraction.IUpdateNewsService;
import com.alkemy.ong.application.util.CommentUtils;
import com.alkemy.ong.application.util.GenericSetPagination;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import com.alkemy.ong.infrastructure.database.mapper.INewsMapper;
import com.alkemy.ong.infrastructure.database.repository.ICategoryRepository;
import com.alkemy.ong.infrastructure.database.repository.INewsRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NewsService extends GenericSetPagination<NewsEntity> implements
    ICreateNewsService, IDeleteNewsService, IUpdateNewsService, IGetNewsService {

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

  @Override
  public ListNewsResponse list(Pageable pageable) {
    Page<NewsEntity> page = newsRepository.findBySoftDeletedFalseOrderByIdAsc(pageable);
    ListNewsResponse listNewsResponse = new ListNewsResponse();
    listNewsResponse.setNews(newsMapper.toListNewsResponse(page.getContent()));
    setPagination(listNewsResponse, page);
    return listNewsResponse;
  }

  @Override
  public NewsResponse getBy(Long id) {
    NewsEntity newsEntity = findBy(id);
    return newsMapper.toNewsResponseWithCategory(newsEntity);
  }

  private NewsEntity findBy(Long id) {
    Optional<NewsEntity> optionalNewsEntity = newsRepository.findById(id);
    if (optionalNewsEntity.isEmpty()
        || Boolean.TRUE.equals(optionalNewsEntity.get().getSoftDeleted())) {
      throw new EntityNotFoundException("News not found.");
    }
    return optionalNewsEntity.get();
  }

  @Override
  public ListCommentsResponse listCommentsByNewsId(Long id) {
    List<Map<String, Object>> queryResponses = newsRepository.findNewsWithAssociatedCommentsBy(id);
    if (queryResponses.isEmpty()) {
      throw new EntityNotFoundException("Comments not found in the news.");
    }
    return ListCommentsResponse.builder()
        .name((String) queryResponses.get(0).get("newsName"))
        .comments(buildCommentResponses(queryResponses))
        .build();
  }

  private List<CommentResponse> buildCommentResponses(List<Map<String, Object>> queryResponses) {
    List<CommentResponse> commentResponses = new ArrayList<>();
    for (Map<String, Object> queryResponse : queryResponses) {
      commentResponses.add(buildCommentResponse(queryResponse));
    }
    return commentResponses;
  }

  private CommentResponse buildCommentResponse(Map<String, Object> queryResponse) {
    String createdBy = CommentUtils.createdBy((String) queryResponse.get("firstName"),
        (String) queryResponse.get("lastName"));
    return CommentResponse.builder()
        .id((Long) queryResponse.get("id"))
        .body((String) queryResponse.get("body"))
        .createdBy(createdBy)
        .createTimestamp((Timestamp) queryResponse.get("createTimestamp"))
        .build();
  }

}
