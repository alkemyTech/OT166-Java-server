package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.exception.OperationNotPermitedException;
import com.alkemy.ong.application.service.abstraction.IDeleteCommentService;
import com.alkemy.ong.application.service.abstraction.IGetCommentService;
import com.alkemy.ong.application.util.SecurityContextHolderUtils;
import com.alkemy.ong.infrastructure.database.entity.CommentEntity;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.database.repository.ICommentRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements IDeleteCommentService, IGetCommentService {

  @Autowired
  private ICommentRepository commentRepository;

  @Autowired
  private SecurityContextHolderUtils securityContextHolderUtils;

  @Override
  public void delete(Long id) {

    CommentEntity commentEntity = this.get(id);
    Long idUserComment = commentEntity.getUser().getId();

    UserEntity userAuthenticated = securityContextHolderUtils.getUserAuthenticated();

    if (idUserComment.equals(userAuthenticated.getId())
        || securityContextHolderUtils.hasAdminRole()) {
      commentRepository.delete(commentEntity);
    } else {
      throw new OperationNotPermitedException("No permission to delete this comment.");
    }
  }

  @Override
  public CommentEntity get(Long id) {
    Optional<CommentEntity> result = commentRepository.findById(id);
    if (result.isEmpty()) {
      throw new EntityNotFoundException("Comment not found");
    }
    return result.get();
  }
}
