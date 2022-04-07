package com.alkemy.ong.bigtest.util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.alkemy.ong.OngApplication;
import com.alkemy.ong.application.rest.request.AuthenticationRequest;
import com.alkemy.ong.infrastructure.database.entity.ActivityEntity;
import com.alkemy.ong.infrastructure.database.entity.CategoryEntity;
import com.alkemy.ong.infrastructure.database.entity.CommentEntity;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import com.alkemy.ong.infrastructure.database.entity.RoleEntity;
import com.alkemy.ong.infrastructure.database.entity.SlideEntity;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import com.alkemy.ong.infrastructure.database.repository.IActivityRepository;
import com.alkemy.ong.infrastructure.database.repository.ICategoryRepository;
import com.alkemy.ong.infrastructure.database.repository.ICommentRepository;
import com.alkemy.ong.infrastructure.database.repository.INewsRepository;
import com.alkemy.ong.infrastructure.database.repository.IOrganizationRepository;
import com.alkemy.ong.infrastructure.database.repository.IRoleRepository;
import com.alkemy.ong.infrastructure.database.repository.ISlideRepository;
import com.alkemy.ong.infrastructure.database.repository.IUserRepository;
import com.alkemy.ong.infrastructure.spring.config.security.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.main.allow-bean-definition-overriding=true",
    classes = OngApplication.class)
@AutoConfigureMockMvc
public abstract class BigTest {

  private static final String ADMIN_EMAIL = "jason@voorhees.com";
  private static final String USER_EMAIL = "freedy@krueger.com";
  private static final String PASSWORD = "abcd1234";

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected PasswordEncoder passwordEncoder;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected IOrganizationRepository organizationRepository;

  @Autowired
  protected ISlideRepository slideRepository;

  @Autowired
  protected IUserRepository userRepository;

  @Autowired
  protected ICommentRepository commentRepository;

  @Autowired
  protected INewsRepository newsRepository;

  @Autowired
  protected ICategoryRepository categoryRepository;

  @Autowired
  protected IRoleRepository roleRepository;

  @Autowired
  protected IActivityRepository activityRepository;

  @Before
  public void setup() {
    createCategoryNews();
    createRoles();
    createUserData();
    deleteAllEntities();
  }

  @After
  public void tearDown() {
    deleteAllEntities();
  }

  private void createUserData() {
    if (userRepository.findByEmail(ADMIN_EMAIL) == null) {
      saveAdminUser();
    }
    if (userRepository.findByEmail(USER_EMAIL) == null) {
      saveStandardUser();
    }
  }

  private void createRoles() {
    if (roleRepository.count() == 0) {
      roleRepository.saveAll(List.of(
          buildRole(Role.USER),
          buildRole(Role.ADMIN)));
    }
  }

  private void createCategoryNews() {
    saveCategory("news");
  }

  protected void cleanUsersData() {
    roleRepository.deleteAll();
    userRepository.deleteAll();
  }

  protected void cleanUsersData(UserEntity... users) {
    userRepository.deleteAllInBatch(Arrays.asList(users));
  }

  protected void cleanActivityData(ActivityEntity... activity) {
    activityRepository.deleteAllInBatch(Arrays.asList(activity));
  }

  private void deleteAllEntities() {
    organizationRepository.deleteAll();
    slideRepository.deleteAll();
    commentRepository.deleteAll();
    newsRepository.deleteAll();
    categoryRepository.deleteAll();
    activityRepository.deleteAll();
  }

  protected void saveOrganizationDetails() {
    organizationRepository.save(OrganizationEntity.builder()
        .name("Somos Mas")
        .image("https://s3.com/logo.jpg/")
        .welcomeText("Welcome to Somos Mas")
        .email("somos.mas@ong.com")
        .phone("+5411345678")
        .address("Elm Street 3")
        .facebookUrl("https://www.facebook.com/Somos_Mas/")
        .linkedInUrl("https://www.linkedin.com/in/Somos-Mas/")
        .instagramUrl("https://www.instagram.com/SomosMas/")
        .build());

    saveSlide();
  }

  protected SlideEntity saveSlide() {
    return slideRepository.save(SlideEntity.builder()
        .order(1)
        .imageUrl("https://s3.com/slide.jpg")
        .build());
  }

  protected CommentEntity saveComment() {
    return commentRepository.save(CommentEntity.builder()
        .user(userRepository.findByEmail(USER_EMAIL))
        .body("My comment.")
        .news(saveNews())
        .build());
  }

  protected NewsEntity saveNews() {
    return newsRepository.save(NewsEntity.builder()
        .image("https://s3.com/news.jpg")
        .content("News content.")
        .name("My first News!!")
        .build());
  }

  protected CategoryEntity saveCategory(String name) {
    return categoryRepository.save(CategoryEntity.builder().name(name).build());
  }

  private void saveStandardUser() {
    userRepository.save(buildUser(
        "Freddy",
        "Krueger",
        USER_EMAIL,
        Role.USER));
  }

  private void saveAdminUser() {
    userRepository.save(buildUser(
        "Jason",
        "Voorhees",
        ADMIN_EMAIL,
        Role.ADMIN));
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

  private ActivityEntity buildActivity(String name, String content, String image) {
    return ActivityEntity.builder()
        .name(name)
        .content(content)
        .image(image)
        .softDeleted(false)
        .build();
  }

  private RoleEntity buildRole(Role role) {
    return RoleEntity.builder()
        .description(role.name())
        .name(role.getFullRoleName())
        .build();
  }

  protected String getAuthorizationTokenForAdminUser() throws Exception {
    return getAuthorizationTokenForUser(ADMIN_EMAIL);
  }

  protected String getAuthorizationTokenForStandardUser() throws Exception {
    return getAuthorizationTokenForUser(USER_EMAIL);
  }

  protected UserEntity getRandomUser() {
    return userRepository.save(buildUser("Bruce", "Wayne", "bruce@wayne.com", Role.USER));
  }

  protected ActivityEntity getRandomActivity() {
    return activityRepository.save(buildActivity(
        "Name Activity",
        "Content Activity",
        "https://s3.com/activity.jpg"));
  }

  private String getAuthorizationTokenForUser(String email) throws Exception {
    String content = mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(AuthenticationRequest.builder()
            .email(email)
            .password(PASSWORD)
            .build()))).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    return JsonPath.read(content, "$.token");
  }
}
