package com.alkemy.ong.application.rest.response;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponse {

  private Long id;
  private String body;
  private String createdBy;
  private String associatedNews;
  private Timestamp createTimestamp;

}
