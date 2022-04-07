package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.CreateCommentRequest;
import com.alkemy.ong.application.rest.response.CommentResponse;

public interface ICreateCommentService {

  CommentResponse save(CreateCommentRequest createCommentRequest);

}
