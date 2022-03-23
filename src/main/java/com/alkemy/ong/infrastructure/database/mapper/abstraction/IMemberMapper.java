package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.request.MemberRequest;
import com.alkemy.ong.application.rest.response.MemberResponse;
import com.alkemy.ong.infrastructure.database.entity.MemberEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IMemberMapper {

  MemberEntity toMemberEntity(MemberRequest request);

  MemberResponse toMemberResponse(MemberEntity entity);
}
