package com.alkemy.ong.application.service;

import com.alkemy.ong.application.exception.EntityNotFoundException;
import com.alkemy.ong.application.rest.request.CreateMemberRequest;
import com.alkemy.ong.application.rest.request.UpdateMemberRequest;
import com.alkemy.ong.application.rest.response.ListMembersResponse;
import com.alkemy.ong.application.rest.response.MemberResponse;
import com.alkemy.ong.application.service.abstraction.ICreateMemberService;
import com.alkemy.ong.application.service.abstraction.IDeleteMemberService;
import com.alkemy.ong.application.service.abstraction.IGetMemberService;
import com.alkemy.ong.application.service.abstraction.IUpdateMemberService;
import com.alkemy.ong.infrastructure.database.entity.ActivityEntity;
import com.alkemy.ong.infrastructure.database.entity.MemberEntity;
import com.alkemy.ong.infrastructure.database.mapper.abstraction.IMemberMapper;
import com.alkemy.ong.infrastructure.database.repository.IMemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService implements ICreateMemberService,
    IGetMemberService,
    IDeleteMemberService, IUpdateMemberService {

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
    MemberEntity memberEntity = findBy(id);
    memberEntity.setSoftDeleted(true);
    memberRepository.save(memberEntity);
  }

  private MemberEntity findBy(Long id) {
    Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
    if (optionalMemberEntity.isEmpty()
        || Boolean.TRUE.equals(optionalMemberEntity.get().getSoftDeleted())) {
      throw new EntityNotFoundException("Member not found.");
    }
    return optionalMemberEntity.get();
  }

  @Override
  public ListMembersResponse listActiveMembers() {
    List<MemberEntity> memberEntities = memberRepository.findBySoftDeletedIsFalse();
    ListMembersResponse listMembersResponse = new ListMembersResponse();
    listMembersResponse.setMembers(memberMapper.toListMemberResponse(memberEntities));
    return listMembersResponse;
  }

  @Override
  public ListMembersResponse findAll(Pageable pageable) {
    Page<MemberEntity> page =
        memberRepository.findBySoftDeletedFalseOrderByCreateTimestampDesc(pageable);
    ListMembersResponse listMembersResponse = new ListMembersResponse();
    listMembersResponse.setMembers(memberMapper.toListMemberResponse(page.getContent()));
    return buildListResponse(listMembersResponse,page);
  }

  private ListMembersResponse buildListResponse(
      ListMembersResponse listM, Page<MemberEntity> page) {
    listM.setPage(page.getNumber());
    listM.setTotalPages(page.getTotalPages());
    listM.setSize(page.getSize());
    return listM;
  }

  @Override
  public MemberResponse update(Long id, UpdateMemberRequest updateMemberRequest) {

    MemberEntity memberUpdate = findBy(id);

    memberUpdate.setName(updateMemberRequest.getName());
    memberUpdate.setFacebookUrl(updateMemberRequest.getFacebookUrl());
    memberUpdate.setInstagramUrl(updateMemberRequest.getInstagramUrl());
    memberUpdate.setLinkedInUrl(updateMemberRequest.getLinkedInUrl());
    memberUpdate.setImage(updateMemberRequest.getImage());
    memberUpdate.setDescription(updateMemberRequest.getDescription());

    memberRepository.save(memberUpdate);

    return memberMapper.toMemberResponse(memberUpdate);
  }
}
