package com.alkemy.ong.infrastructure.database.mapper;

import com.alkemy.ong.application.rest.request.CreateCommentRequest;
import com.alkemy.ong.application.rest.response.CommentResponse;
import com.alkemy.ong.infrastructure.database.entity.CommentEntity;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ICommentMapper {

  CommentEntity toCommentEntity(CreateCommentRequest createCommentRequest);

  CommentResponse toCommentResponse(CommentEntity commentEntity);

  @Named("commentsWithoutId")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createTimestamp", ignore = true)
  CommentResponse toCommentsWithoutId(CommentEntity commentEntity);

  @IterableMapping(qualifiedByName = "commentsWithoutId")
  List<CommentResponse> toCommentsResponse(List<CommentEntity> commentEntities);

}
