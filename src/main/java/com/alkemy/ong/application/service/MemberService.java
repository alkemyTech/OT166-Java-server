package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.request.CreateMemberRequest;
import com.alkemy.ong.application.rest.response.MemberResponse;
import com.alkemy.ong.application.service.abstraction.ICreateMemberService;
import com.alkemy.ong.application.service.abstraction.IDeleteMemberService;
import com.alkemy.ong.infrastructure.database.entity.MemberEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IMemberMapper;
import com.alkemy.ong.infrastructure.database.repository.IMemberRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService implements ICreateMemberService, IDeleteMemberService {

  @Autowired
  private IMemberRepository memberRepository;

  @Autowired
  private IMemberMapper memberMapper;

  @Override
  public MemberResponse save(CreateMemberRequest createMemberRequest) {
    MemberEntity memberEntity = memberMapper.toMemberEntity(createMemberRequest);
    memberEntity.setSoftDeleted(false);
    return memberMapper.toMemberResponse(memberRepository.save(memberEntity));
  }

  @Override
  public void delete(Long id) {
    Optional<MemberEntity> result = memberRepository.findById(id);
    if (result.isEmpty() || Boolean.TRUE.equals(result.get().getSoftDeleted())) {
      throw new EntityNotFoundException("Member not found.");
    }

    MemberEntity memberEntity = result.get();
    memberEntity.setSoftDeleted(true);
    memberRepository.save(memberEntity);
  }

}
