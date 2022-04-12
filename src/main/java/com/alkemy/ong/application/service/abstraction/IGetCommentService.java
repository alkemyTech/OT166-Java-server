package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.ListCommentsResponse;

public interface IGetCommentService {

  ListCommentsResponse listCommentsByNewsId(Long id);

}
