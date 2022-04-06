package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.request.CreateContactRequest;
import com.alkemy.ong.application.rest.response.ContactResponse;
import com.alkemy.ong.infrastructure.database.entity.ContactEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IContactMapper {

  ContactEntity toContactEntity(CreateContactRequest createContactRequest);

  ContactResponse toContactResponse(ContactEntity contactEntity);

  List<ContactResponse> toListContactResponse(List<ContactEntity> contactEntities);

}
