package com.alkemy.ong.infrastructure.database.repository;

import com.alkemy.ong.infrastructure.database.entity.SlideEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISlideRepository extends JpaRepository<SlideEntity, Long> {

}