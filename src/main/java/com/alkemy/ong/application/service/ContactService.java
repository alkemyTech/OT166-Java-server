package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.SendEmailException;
import com.alkemy.ong.application.rest.request.CreateContactRequest;
import com.alkemy.ong.application.rest.response.ContactResponse;
import com.alkemy.ong.application.rest.response.ListContactsResponse;
import com.alkemy.ong.application.service.abstraction.ICreateContactService;
import com.alkemy.ong.application.service.abstraction.IGetContactService;
import com.alkemy.ong.application.util.mail.EmailDelegate;
import com.alkemy.ong.application.util.mail.template.ContactEmailTemplate;
import com.alkemy.ong.infrastructure.database.entity.ContactEntity;
import com.alkemy.ong.infrastructure.database.mapper.IContactMapper;
import com.alkemy.ong.infrastructure.database.repository.IContactRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ContactService implements ICreateContactService, IGetContactService {

  @Autowired
  private IContactRepository contactRepository;

  @Autowired
  private IContactMapper contactMapper;

  @Autowired
  private EmailDelegate emailDelegate;

  @Override
  public ContactResponse save(CreateContactRequest contactRequest) {
    ContactEntity contactEntity = contactRepository.save(
        contactMapper.toContactEntity(contactRequest));
    sendEmail(contactEntity);
    return contactMapper.toContactResponse(contactEntity);
  }

  private void sendEmail(ContactEntity contactEntity) {
    ContactEmailTemplate template = new ContactEmailTemplate(
        contactEntity.getEmail(),
        contactEntity.getName());

    try {
      emailDelegate.send(template);
    } catch (SendEmailException e) {
      log.error(e.getMessage());
    }
  }

  @Override
  public ListContactsResponse listActiveContacts() {
    List<ContactEntity> contactEntities = contactRepository.findAllByDeletedAtIsNull();
    ListContactsResponse listContactsResponse = new ListContactsResponse();
    listContactsResponse.setContacts(contactMapper.toListContactResponse(contactEntities));
    return listContactsResponse;
  }
}
