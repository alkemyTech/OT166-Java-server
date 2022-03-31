package com.alkemy.ong.application.service;

import com.alkemy.ong.application.rest.request.CreateContactRequest;
import com.alkemy.ong.application.rest.response.ContactResponse;
import com.alkemy.ong.application.service.abstraction.ICreateContactService;
import com.alkemy.ong.infrastructure.database.entity.ContactEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IContactMapper;
import com.alkemy.ong.infrastructure.database.repository.IContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactService implements ICreateContactService {

  @Autowired
  private IContactRepository contactRepository;

  @Autowired
  private IContactMapper contactMapper;

  @Override
  public ContactResponse save(CreateContactRequest contactRequest) {
    ContactEntity contactEntity = contactMapper.toContactEntity(contactRequest);
    return contactMapper.toContactResponse(contactRepository.save(contactEntity));
  }
}