package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.request.RegisterRequest;
import com.alkemy.ong.application.rest.response.RegisterResponse;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {

  UserEntity toUserEntity(RegisterRequest registerRequest);

  RegisterResponse toRegisterResponse(UserEntity userEntity);

}
