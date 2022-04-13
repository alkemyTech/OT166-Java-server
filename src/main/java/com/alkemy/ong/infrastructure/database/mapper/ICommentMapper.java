package com.alkemy.ong.infrastructure.database.mapper;

import com.alkemy.ong.application.rest.request.CreateCommentRequest;
import com.alkemy.ong.application.rest.response.CommentResponse;
import com.alkemy.ong.infrastructure.database.entity.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICommentMapper {

  CommentEntity toCommentEntity(CreateCommentRequest createCommentRequest);

  CommentResponse toCommentResponse(CommentEntity commentEntity);

}
