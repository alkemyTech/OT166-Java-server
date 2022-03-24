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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEMBERS")
public class MemberEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MEMBER_ID")
  private Long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "FACEBOOK_URL")
  private String facebookUrl;

  @Column(name = "INSTAGRAM_URL")
  private String instagramUrl;

  @Column(name = "LINKEDIN_URL")
  private String linkedInUrl;

  @Column(name = "IMAGE", nullable = false)
  private String image;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "SOFT_DELETED")
  private Boolean softDeleted;

  @CreationTimestamp
  @Column(name = "CREATE_TIMESTAMP")
  private Timestamp createTimestamp;

}
