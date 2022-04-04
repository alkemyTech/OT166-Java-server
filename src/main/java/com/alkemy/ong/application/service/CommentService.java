package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.exception.OperationNotPermittedException;
import com.alkemy.ong.application.service.abstraction.IDeleteCommentService;
import com.alkemy.ong.application.util.SecurityUtils;
import com.alkemy.ong.infrastructure.database.entity.CommentEntity;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.database.repository.ICommentRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentService implements IDeleteCommentService {

  @Autowired
  private ICommentRepository commentRepository;

  @Autowired
  private SecurityUtils securityUtils;

  @Override
  public void delete(Long id) {
    CommentEntity commentEntity = findBy(id);
    validateIfOperationIsAllowed(commentEntity.getUser());
    commentRepository.delete(commentEntity);
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

}
