package com.alkemy.ong.infrastructure.database.repository;

import com.alkemy.ong.infrastructure.database.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberRepository extends JpaRepository<MemberEntity, Long> {

  Page<MemberEntity> findBySoftDeletedIsFalse(Pageable pageable);

}
