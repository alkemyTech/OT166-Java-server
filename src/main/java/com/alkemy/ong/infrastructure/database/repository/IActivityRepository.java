package com.alkemy.ong.infrastructure.database.repository;

import com.alkemy.ong.infrastructure.database.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IActivityRepository extends JpaRepository<ActivityEntity, Long> {

  @Query("SELECT c FROM ActivityEntity c WHERE c.id=:id AND c.softDeleted=false")
  ActivityEntity findByIdAndSoftDelete(@Param("id") Long id);

}
