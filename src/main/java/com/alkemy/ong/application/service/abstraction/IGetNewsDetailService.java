package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.NewsDetailResponse;

public interface IGetNewsDetailService {

  NewsDetailResponse getBy(Long id);

}
