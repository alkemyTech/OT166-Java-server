package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.exception.OperationNotPermittedException;
import com.alkemy.ong.application.rest.request.CreateCommentRequest;
import com.alkemy.ong.application.rest.response.CommentResponse;
import com.alkemy.ong.application.service.abstraction.ICreateCommentService;
import com.alkemy.ong.application.service.abstraction.IDeleteCommentService;
import com.alkemy.ong.application.util.SecurityUtils;
import com.alkemy.ong.infrastructure.database.entity.CommentEntity;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.ICommentMapper;
import com.alkemy.ong.infrastructure.database.repository.ICommentRepository;
import com.alkemy.ong.infrastructure.database.repository.INewsRepository;
import com.alkemy.ong.infrastructure.database.repository.IUserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentService implements IDeleteCommentService, ICreateCommentService {

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

    commentEntity.setUser(userEntity);
    commentEntity.setNews(newsEntity);

    return commentMapper.toCommentResponse(commentRepository.save(commentEntity));
  }

  private void validateIfOperationIsAllowed(UserEntity userEntity) {
    if (!securityUtils.hasAdminRole()
        && !userEntity.equals(securityUtils.getUserAuthenticated())) {
      throw new OperationNotPermittedException("No permission to delete this comment.");
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

}
