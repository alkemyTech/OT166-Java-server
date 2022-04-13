package com.alkemy.ong.application.rest.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ListCommentsResponse {

  private String name;
  private List<CommentResponse> comments;

}
