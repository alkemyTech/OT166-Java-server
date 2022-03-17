package com.alkemy.ong.infrastructure.database.entity;

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
import org.hibernate.annotations.CreationTimestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACTIVITIES")
public class ActivityEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ACTIVITY_ID")
  private Long activityId;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "CONTENT", nullable = false)
  private String content;

  @Column(name = "IMAGE", nullable = false)
  private String image;

  @Column(name = "SOFT_DELETE")
  private Boolean softDelete;

  @Column(name = "CREATE_TIMESTAMP")
  @CreationTimestamp
  private Timestamp createTimpestamp;

}
