package com.alkemy.ong.infrastructure.database.repository;

import com.alkemy.ong.infrastructure.database.entity.CommentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<CommentEntity, Long> {

  @Query(value = "FROM CommentEntity c WHERE c.news.id = :id")
  List<CommentEntity> findByNewsId(@Param("id") Long id);

}
