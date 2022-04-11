package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.UpdateMemberRequest;
import com.alkemy.ong.application.rest.response.MemberResponse;

public interface IUpdateMemberService {

  MemberResponse update(Long id, UpdateMemberRequest updateMemberRequest);

}
