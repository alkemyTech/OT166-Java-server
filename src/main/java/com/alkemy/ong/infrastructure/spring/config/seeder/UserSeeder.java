package com.alkemy.ong.infrastructure.spring.config.seeder;

import com.alkemy.ong.infrastructure.database.entity.RoleEntity;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.database.repository.IRoleRepository;
import com.alkemy.ong.infrastructure.database.repository.IUserRepository;
import com.alkemy.ong.infrastructure.spring.config.security.Role;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserSeeder {

  private static final List<String> ADMIN_FIRST_NAMES = List.of("Magali", "Nicole", "Florencia",
      "Santiago", "Romina");
  private static final List<String> ADMIN_LAST_NAMES = List.of("Kain", "Rappoport", "Rosental",
      "Russo", "Fausti");
  private static final List<String> ADMIN_EMAILS = List.of("maga@gmail.com", "nicole.rap@gmail.com",
      "flor.rosental@gmail.com", "santi.russo@gmail.com", "romina.fausti@gmail.com");
  private static final List<String> USER_FIRST_NAMES = List.of("Matias", "Matias", "Jair", "Adrian",
      "Facundo");
  private static final List<String> USER_LAST_NAMES = List.of("Espinola", "Fiorentini", "Garcia",
      "Luna", "Villegas");
  private static final List<String> USER_EMAILS = List.of("matias.espinola@outlook.com",
      "matias.fiorentini@gmail.com", "jair.garica@gmail.com", "adrian.luna@gmail.com",
      "facundo.villegas@gmail.com");

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private IUserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private IRoleRepository roleRepository;

  @EventListener
  @Order(Ordered.LOWEST_PRECEDENCE)
  public void seed(ContextRefreshedEvent event) {
    createUserTable();
  }

  private void createUserTable() {
    if (userRepository.count() < 2) {
      createUsers(USER_FIRST_NAMES, USER_LAST_NAMES, USER_EMAILS, Role.USER);
      createUsers(ADMIN_FIRST_NAMES, ADMIN_LAST_NAMES, ADMIN_EMAILS, Role.ADMIN);
      log.info("Ten new users have been created.");
    } else {
      log.info("No user seeding required.");
    }
  }

  private void createUsers(List<String> firstNames, List<String> lastnames, List<String> emails,
      Role role) {
    RoleEntity userRole = roleRepository.findByName(role.getFullRoleName());
    for (int i = 0; i < 5; i++) {
      UserEntity newNormalUser = UserEntity.builder()
          .firstName(firstNames.get(i))
          .lastName(lastnames.get(i))
          .email(emails.get(i))
          .password(passwordEncoder.encode("pass123"))
          .role(userRole)
          .softDeleted(Boolean.FALSE)
          .createTimestamp(Timestamp.from(Instant.now()))
          .build();
      userRepository.save(newNormalUser);
    }
  }

}
