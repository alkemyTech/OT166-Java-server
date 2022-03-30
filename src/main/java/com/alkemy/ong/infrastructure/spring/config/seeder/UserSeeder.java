package com.alkemy.ong.infrastructure.spring.config.seeder;

import com.alkemy.ong.infrastructure.database.entity.RoleEntity;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.database.repository.IRoleRepository;
import com.alkemy.ong.infrastructure.database.repository.IUserRepository;
import com.alkemy.ong.infrastructure.spring.config.security.Role;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
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

  private static final String PASSWORD = "pass123";

  @Autowired
  private IUserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private IRoleRepository roleRepository;

  @EventListener
  public void seed(ContextRefreshedEvent event) {
    createUserTable();
  }

  private void createUserTable() {
    createRoles();
    createUser();
  }

  private void createRoles() {
    if (roleRepository.count() < 2) {
      roleRepository.saveAll(List.of(
          buildRole(Role.USER),
          buildRole(Role.ADMIN)));
    }
  }

  private RoleEntity buildRole(Role role) {
    return RoleEntity.builder()
        .name(role.getFullRoleName())
        .description(role.name())
        .build();
  }

  private void createUser() {
    if (userRepository.count() < 10) {
      saveUsers(USER_FIRST_NAMES, USER_LAST_NAMES, USER_EMAILS, Role.USER);
      saveUsers(ADMIN_FIRST_NAMES, ADMIN_LAST_NAMES, ADMIN_EMAILS, Role.ADMIN);
    }
  }

  private void saveUsers(List<String> firstNames, List<String> lastNames, List<String> emails,
      Role role) {
    List<UserEntity> users = new ArrayList<>(5);
    for (int index = 0; index < 5; index++) {
      users.add(buildUser(
          firstNames.get(index),
          lastNames.get(index),
          emails.get(index),
          role));
    }
    userRepository.saveAll(users);
  }

  private UserEntity buildUser(String firstName, String lastName, String email, Role role) {
    return UserEntity.builder()
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .password(passwordEncoder.encode(PASSWORD))
        .role(roleRepository.findByName(role.getFullRoleName()))
        .softDeleted(false)
        .build();
  }

}
