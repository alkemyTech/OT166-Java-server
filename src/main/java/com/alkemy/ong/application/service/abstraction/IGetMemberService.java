package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.ListMembersResponse;
import org.springframework.data.domain.Pageable;

public interface IGetMemberService {

  ListMembersResponse listActiveMembers();

  ListMembersResponse findAll(Pageable pageable);

}
