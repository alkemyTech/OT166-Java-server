package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.MemberRequest;
import com.alkemy.ong.application.rest.response.MemberResponse;

public interface ISaveMember {

  MemberResponse save(MemberRequest member);
}
