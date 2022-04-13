package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.ListCommentsResponse;
import com.alkemy.ong.application.rest.response.ListNewsResponse;
import com.alkemy.ong.application.rest.response.NewsResponse;
import org.springframework.data.domain.Pageable;

public interface IGetNewsService {

  ListNewsResponse list(Pageable pageable);

  NewsResponse getBy(Long id);

  ListCommentsResponse listCommentsByNewsId(Long id);

}
