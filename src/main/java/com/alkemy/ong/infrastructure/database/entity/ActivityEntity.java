package com.alkemy.ong.infrastructure.database.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACTIVITIES")
@SQLDelete(sql = "UPDATE ACTIVITIES SET DELETED = true WHERE ACTIVITY_ID=?")
@Where(clause = "DELETED=false")
public class ActivityEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ACTIVITY_ID")
  private Long activityId;

  @Column(name = "NAME")
  @NotNull
  private String name;

  @Column(name = "CONTENT")
  @NotNull
  private String content;

  @Column(name = "IMAGE")
  @NotNull
  private String image;

  @Column(name = "DELETED")
  private boolean deleted = Boolean.FALSE;

  private Timestamp createTimpestamps;

}
