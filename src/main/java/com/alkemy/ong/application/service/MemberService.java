package com.alkemy.ong.application.service;

import com.alkemy.ong.application.rest.request.MemberRequest;
import com.alkemy.ong.application.rest.response.MemberResponse;
import com.alkemy.ong.application.service.abstraction.ISaveMember;
import com.alkemy.ong.infrastructure.database.entity.MemberEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IMemberMapper;
import com.alkemy.ong.infrastructure.database.repository.IMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService implements ISaveMember {

  @Autowired
  private IMemberRepository memberRepository;

  @Autowired
  private IMemberMapper memberMapper;

  @Override
  public MemberResponse save(MemberRequest request) {
    MemberEntity entity = memberMapper.toMemberEntity(request);
    entity.setSoftDeleted(false);
    return memberMapper.toMemberResponse(memberRepository.save(entity));
  }
}
