package com.alkemy.ong.infrastructure.spring.config.seeder;

import com.alkemy.ong.infrastructure.database.entity.RoleEntity;
import com.alkemy.ong.infrastructure.database.repository.IRoleRepository;
import com.alkemy.ong.infrastructure.spring.config.security.Role;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoleSeeder {

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private IRoleRepository roleRepository;

  @EventListener
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public void seed(ContextRefreshedEvent event) {
    createRoleTable();
  }

  private void createRoleTable() {
    if (roleRepository.count() < 2) {
      createRole(Role.USER);
      createRole(Role.ADMIN);
      log.info("Two new roles have been created.");
    } else {
      log.info("No role seeding required.");
    }
  }

  private void createRole(Role role) {
    RoleEntity newRole = RoleEntity.builder()
        .name(role.getFullRoleName())
        .description(role.name())
        .createTimestamp(Timestamp.from(Instant.now()))
        .build();
    roleRepository.save(newRole);
  }
}
