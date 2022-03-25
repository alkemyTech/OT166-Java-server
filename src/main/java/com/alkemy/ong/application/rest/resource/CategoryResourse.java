package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CategoryRequest;
import com.alkemy.ong.application.rest.response.CategoryResponse;
import com.alkemy.ong.application.service.abstraction.ICreateCategoryService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/categories")
public class CategoryResourse {

  @Autowired
  private ICreateCategoryService categoryService;

  @PostMapping
  public ResponseEntity<CategoryResponse> saveCategory(
      @Valid @RequestBody CategoryRequest categoryRequest) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(categoryService.createCategory(categoryRequest));
  }
}
