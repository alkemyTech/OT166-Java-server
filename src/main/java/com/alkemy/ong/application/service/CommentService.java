package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.exception.OperationNotPermittedException;
import com.alkemy.ong.application.rest.request.CreateCommentRequest;
import com.alkemy.ong.application.rest.request.UpdateCommentRequest;
import com.alkemy.ong.application.rest.response.CommentResponse;
import com.alkemy.ong.application.rest.response.ListCommentsResponse;
import com.alkemy.ong.application.service.abstraction.ICreateCommentService;
import com.alkemy.ong.application.service.abstraction.IDeleteCommentService;
import com.alkemy.ong.application.service.abstraction.IGetCommentService;
import com.alkemy.ong.application.service.abstraction.IUpdateCommentService;
import com.alkemy.ong.application.util.CommentUtils;
import com.alkemy.ong.application.util.SecurityUtils;
import com.alkemy.ong.infrastructure.database.entity.CommentEntity;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.database.mapper.ICommentMapper;
import com.alkemy.ong.infrastructure.database.repository.ICommentRepository;
import com.alkemy.ong.infrastructure.database.repository.INewsRepository;
import com.alkemy.ong.infrastructure.database.repository.IUserRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentService implements IDeleteCommentService, ICreateCommentService,
    IUpdateCommentService, IGetCommentService {

  @Autowired
  private ICommentRepository commentRepository;

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private INewsRepository newsRepository;

  @Autowired
  private SecurityUtils securityUtils;

  @Autowired
  private ICommentMapper commentMapper;

  @Override
  public void delete(Long id) {
    CommentEntity commentEntity = findBy(id);
    validateIfOperationIsAllowed(commentEntity.getUser());
    commentRepository.delete(commentEntity);
  }

  @Override
  public CommentResponse save(CreateCommentRequest createCommentRequest) {
    UserEntity userEntity = getUser(createCommentRequest.getUserId());
    NewsEntity newsEntity = getNews(createCommentRequest.getNewsId());

    CommentEntity commentEntity = commentMapper.toCommentEntity(
        createCommentRequest);

    buildCommentEntity(commentEntity, userEntity, newsEntity);

    CommentResponse commentResponse =
        commentMapper.toCommentResponse(commentRepository.save(commentEntity));

    setAdditionalInformation(commentResponse, userEntity, newsEntity);

    return commentResponse;
  }

  @Override
  public ListCommentsResponse list() {
    List<CommentEntity> commentEntities = commentRepository.findAll(Sort.by("createTimestamp"));
    ListCommentsResponse listCommentsResponse = new ListCommentsResponse();
    listCommentsResponse.setComments(commentMapper.toCommentsResponse(commentEntities));
    return listCommentsResponse;
  }

  private void validateIfOperationIsAllowed(UserEntity userEntity) {
    if (!securityUtils.hasAdminRole()
        && !userEntity.equals(securityUtils.getUserAuthenticated())) {
      throw new OperationNotPermittedException("No permission to perform this operation.");
    }
  }

  private CommentEntity findBy(Long id) {
    Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(id);
    if (optionalCommentEntity.isEmpty()) {
      throw new EntityNotFoundException("Comment not found.");
    }
    return optionalCommentEntity.get();
  }

  private UserEntity getUser(Long id) {
    UserEntity userEntity = userRepository.findByIdAndSoftDeletedFalse(id);
    if (userEntity == null) {
      throw new EntityNotFoundException("User not found");
    }
    return userEntity;
  }

  private NewsEntity getNews(Long id) {
    NewsEntity newsEntity = newsRepository.findByIdAndSoftDeletedFalse(id);
    if (newsEntity == null) {
      throw new EntityNotFoundException("News not found");
    }
    return newsEntity;
  }

  private void buildCommentEntity(
      CommentEntity commentEntity, UserEntity userEntity, NewsEntity newsEntity) {
    commentEntity.setUser(userEntity);
    commentEntity.setNews(newsEntity);
  }

  private void setAdditionalInformation(
      CommentResponse commentResponse, UserEntity userEntity, NewsEntity newsEntity) {
    commentResponse.setCreatedBy(
        CommentUtils.createdBy(userEntity.getFirstName(), userEntity.getLastName()));
    commentResponse.setAssociatedNews(newsEntity.getName());
  }

  @Override
  public CommentResponse update(Long id, UpdateCommentRequest updateCommentRequest) {
    CommentEntity commentUpdate = findBy(id);
    validateIfOperationIsAllowed(commentUpdate.getUser());

    UserEntity userEntity = commentUpdate.getUser();
    NewsEntity newsEntity = commentUpdate.getNews();

    commentUpdate.setBody(updateCommentRequest.getBody());

    CommentResponse commentResponse =
        commentMapper.toCommentResponse(commentRepository.save(commentUpdate));

    setAdditionalInformation(commentResponse, userEntity, newsEntity);
    return commentResponse;
  }

}
