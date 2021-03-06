package com.alkemy.ong.infrastructure.database.repository;

import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrganizationRepository extends JpaRepository<OrganizationEntity, Long> {

}
