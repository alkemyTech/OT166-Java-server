package com.alkemy.ong.infrastructure.database.repository;

import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface INewsRepository extends JpaRepository<NewsEntity, Long> {

  NewsEntity findByIdAndSoftDeletedFalse(Long id);

  Page<NewsEntity> findBySoftDeletedFalseOrderByIdAsc(Pageable pageable);

  @Query(value = "SELECT n.name AS newsName, c.id AS id, c.body AS body, "
      + "c.user.firstName AS firstName, c.user.lastName AS lastName, "
      + "c.createTimestamp AS createTimestamp "
      + "FROM NewsEntity AS n "
      + "INNER JOIN CommentEntity AS c "
      + "ON c.news.id = :id "
      + "WHERE n.softDeleted = false")
  List<Map<String, Object>> findByNewsId(@Param("id") Long id);

}
