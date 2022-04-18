package com.alkemy.ong.application.util;

import com.alkemy.ong.application.rest.response.PaginationResponse;
import org.springframework.data.domain.Page;

public class GenericSetPagination<T> {

  public void setPagination(PaginationResponse paginationResponse, Page<T> page) {
    paginationResponse.setPage(page.getNumber());
    paginationResponse.setTotalPages(page.getTotalPages());
    paginationResponse.setSize(page.getSize());
  }

}
