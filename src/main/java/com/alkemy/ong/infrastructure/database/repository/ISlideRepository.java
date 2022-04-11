package com.alkemy.ong.infrastructure.database.repository;

import com.alkemy.ong.infrastructure.database.entity.SlideEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ISlideRepository extends JpaRepository<SlideEntity, Long> {

  List<SlideEntity> findAllByOrderByOrder();

  @Query(value = "SELECT COALESCE(MAX(s.order), 0) FROM SlideEntity s")
  int getHighestOrder();

}