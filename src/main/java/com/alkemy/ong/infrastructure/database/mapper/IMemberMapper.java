package com.alkemy.ong.infrastructure.database.mapper;

import com.alkemy.ong.application.rest.request.CreateMemberRequest;
import com.alkemy.ong.application.rest.response.MemberResponse;
import com.alkemy.ong.infrastructure.database.entity.MemberEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IMemberMapper {

  MemberEntity toMemberEntity(CreateMemberRequest createMemberRequest);

  MemberResponse toMemberResponse(MemberEntity entity);

  List<MemberResponse> toListMemberResponse(List<MemberEntity> memberEntities);
}
