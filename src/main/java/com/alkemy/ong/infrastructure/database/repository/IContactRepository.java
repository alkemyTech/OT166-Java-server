package com.alkemy.ong.infrastructure.database.repository;

import com.alkemy.ong.infrastructure.database.entity.ContactEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactRepository extends JpaRepository<ContactEntity, Long> {

  List<ContactEntity> findAllByDeletedAtIsNull();

}
