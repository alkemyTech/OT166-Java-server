package com.alkemy.ong.infrastructure.database.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORGANIZATIONS")
public class OrganizationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORGANIZATION_ID")
  private Long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "IMAGE", nullable = false)
  private String image;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "PHONE")
  private String phone;

  @Column(name = "EMAIL", nullable = false)
  private String email;

  @Column(name = "WELCOME_TEXT", nullable = false)
  private String welcomeText;

  @Column(name = "ABOUT_US_TEXT")
  private String aboutUsText;

  @Column(name = "FACEBOOK_URL")
  private String facebookUrl;

  @Column(name = "LINKEDIN_URL")
  private String linkedInUrl;

  @Column(name = "INSTAGRAM_URL")
  private String instagramUrl;

  @CreationTimestamp
  @Column(name = "CREATE_TIMESTAMP")
  private Timestamp createTimestamp;

  @Column(name = "SOFT_DELETED")
  private Boolean softDeleted;
}
