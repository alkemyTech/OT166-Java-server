package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.request.UpdateCommentRequest;
import com.alkemy.ong.application.rest.response.CommentResponse;

public interface IUpdateCommentService {

  CommentResponse update(Long id, UpdateCommentRequest updateCommentRequest);

}
