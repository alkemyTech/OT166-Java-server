package com.alkemy.ong.infrastructure.database.repository;

import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INewsRepository extends JpaRepository<NewsEntity, Long> {

  NewsEntity findByIdAndSoftDeletedFalse(Long id);

  Page<NewsEntity> findBySoftDeletedFalseOrderByIdAsc(Pageable pageable);

}
