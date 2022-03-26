package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.service.abstraction.IDeleteCommentService;
import com.alkemy.ong.infrastructure.database.entity.CommentEntity;
import com.alkemy.ong.infrastructure.database.repository.ICommentRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements IDeleteCommentService {

  @Autowired
  private ICommentRepository commentRepository;

  @Override
  public void delete(Long id) {
    Optional<CommentEntity> result = commentRepository.findById(id);
    if (result.isEmpty()) {
      throw new EntityNotFoundException("Comment not found.");
    }
  }
}
