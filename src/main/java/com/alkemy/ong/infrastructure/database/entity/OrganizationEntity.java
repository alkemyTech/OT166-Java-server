package com.alkemy.ong.infrastructure.database.entity;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "ORGANIZATIONS")
public class OrganizationEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORGANIZATIONS_ID")
  private Long organizationId;
  
  @NotNull
  @Column(name = "NAME")
  private String name;
  
  @NotNull
  @Column(name = "IMAGE")
  private String image;
  
  @Nullable
  @Column(name = "ADDRESS")
  private String address;
  
  @Nullable
  @Column(name = "PHONE")
  private String phone;
  
  @NotNull
  @Column(name = "EMAIL")
  private String email;
  
  @NotNull
  @Column(name = "WELCOME_TEXT")
  private String welcomeText;
  
  @Nullable
  @Column(name = "ABOUT_US_TEXT")
  private String aboutUsText;
  
  @Column(name = "CREATE_TIMESTAMPS")
  private Timestamp createTimestamps = new Timestamp(System.currentTimeMillis());
  
  @Column(name = "SOFTDELETE")
  private Boolean softDelete;
}
