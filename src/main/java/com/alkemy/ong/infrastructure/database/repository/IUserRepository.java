package com.alkemy.ong.infrastructure.database.repository;

import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import java.util.List;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findByEmail(String email);


  @Query(value = "SELECT U FROM UserEntity U WHERE U.softDeleted=0")
  List<UserEntity> findAllActiveUsers();


}
