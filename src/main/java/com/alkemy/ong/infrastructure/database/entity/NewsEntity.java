package com.alkemy.ong.infrastructure.database.entity;

import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "NEWS")
public class NewsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "NEWS_ID")
  private Long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "CONTENT", nullable = false, columnDefinition = "text")
  private String content;

  @Column(name = "IMAGE", nullable = false)
  private String image;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "CATEGORY_ID", insertable = false, updatable = false)
  private CategoryEntity category;

  @Column(name = "CATEGORY_ID", nullable = false)
  private Long categoryId;


  @Column(name = "CREATE_TIMESTAMP")
  @CreationTimestamp
  private Timestamp createTimestamp;

  @Column(name = "SOFT_DELETED")
  private Boolean softDeleted;
}
