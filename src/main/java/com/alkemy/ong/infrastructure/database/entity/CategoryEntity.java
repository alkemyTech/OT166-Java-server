package com.alkemy.ong.infrastructure.database.entity;

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
import java.sql.Timestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CATEGORIES")
@SQLDelete(sql = "UPDATE categories SET soft_deleted = true WHERE id=?")
@Where(clause = "soft_deleted=false")
public class CategoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CATEGORY_ID")
  private Long categoryId;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "IMAGE")
  private String image;

  @Column(name = "CREATE_TIMESTAMPS")
  private Timestamp createTimeStamps = new Timestamp(System.currentTimeMillis());

  @Column(name = "SOFT_DELETED")
  private boolean softDeleted = Boolean.FALSE;

}
