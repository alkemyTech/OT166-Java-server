package com.alkemy.ong.infrastructure.database.entity;

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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "SLIDES")
public class SlideEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "SLIDE_ID")
  private Long id;

  @Column(name = "IMAGE_URL", nullable = false)
  private String imageUrl;

  @Column(name = "TEXT")
  private String text;

  @Column(name = "SLIDE_ORDER", nullable = false)
  private Integer order;

}
