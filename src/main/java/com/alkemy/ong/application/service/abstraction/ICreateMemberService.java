package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.CreateMemberRequest;
import com.alkemy.ong.application.rest.response.MemberResponse;

public interface ICreateMemberService {

  MemberResponse save(CreateMemberRequest member);

}
