package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.CreateContactRequest;
import com.alkemy.ong.application.rest.response.ContactResponse;

public interface ICreateContactService {

  ContactResponse save(CreateContactRequest contactRequest);

}
