package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.infrastructure.database.entity.CommentEntity;

public interface IGetCommentService {

  CommentEntity get(Long id);
}
