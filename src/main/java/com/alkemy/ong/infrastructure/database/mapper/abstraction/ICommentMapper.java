package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.request.CreateCommentRequest;
import com.alkemy.ong.application.rest.response.CommentResponse;
import com.alkemy.ong.infrastructure.database.entity.CommentEntity;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ICommentMapper {

  CommentEntity toCommentEntity(CreateCommentRequest createCommentRequest);

  CommentResponse toCommentResponse(CommentEntity commentEntity);

  @IterableMapping(qualifiedByName = "nameCorrection")
  List<CommentResponse> toListCommentsResponse(List<CommentEntity> commentEntities);

  @Named("nameCorrection")
  @Mappings({
      @Mapping(target = "createdBy", source = "user.email"),
      @Mapping(target = "associatedNews", ignore = true)
  })
  CommentResponse commentResponseSpecifications(CommentEntity comment);

}
