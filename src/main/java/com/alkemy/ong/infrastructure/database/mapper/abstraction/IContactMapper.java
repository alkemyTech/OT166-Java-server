package com.alkemy.ong.infrastructure.database.mapper.abstraction;

import com.alkemy.ong.application.rest.request.CreateContactRequest;
import com.alkemy.ong.application.rest.response.ContactResponse;
import com.alkemy.ong.infrastructure.database.entity.ContactEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IContactMapper {

  ContactResponse toContactResponse(ContactEntity contactEntity);

  ContactEntity toContactEntity(CreateContactRequest createContactRequest);

}
