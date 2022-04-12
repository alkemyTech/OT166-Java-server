package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateCategoryRequest;
import com.alkemy.ong.application.rest.request.UpdateCategoryRequest;
import com.alkemy.ong.application.rest.response.CategoryResponse;
import com.alkemy.ong.application.rest.response.ListCategoriesResponse;
import com.alkemy.ong.application.service.abstraction.ICreateCategoryService;
import com.alkemy.ong.application.service.abstraction.IDeleteCategoryService;
import com.alkemy.ong.application.service.abstraction.IGetCategoryService;
import com.alkemy.ong.application.service.abstraction.IUpdateCategoryService;
import com.alkemy.ong.application.util.PaginatedResultsRetrieved;
import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("categories")
public class CategoryResource {

  @Autowired
  private ICreateCategoryService createCategoryService;

  @Autowired
  private IDeleteCategoryService deleteCategoryService;

  @Autowired
  private IGetCategoryService getCategoryService;

  @Autowired
  private IUpdateCategoryService updateCategoryService;

  @Autowired
  private PaginatedResultsRetrieved paginatedResultsRetrieved;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryResponse> create(
      @Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
    CategoryResponse categoryResponse = createCategoryService.create(createCategoryRequest);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(categoryResponse.getId())
        .toUri();

    return ResponseEntity.created(location).body(categoryResponse);
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryResponse> update(@PathVariable Long id,
      @Valid @RequestBody UpdateCategoryRequest updateCategoryRequest) {
    return ResponseEntity.ok().body(updateCategoryService.update(id, updateCategoryRequest));
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteCategoryService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryResponse> getBy(@PathVariable Long id) {
    return ResponseEntity.ok().body(getCategoryService.getBy(id));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListCategoriesResponse> listActiveCategories(Pageable pageable,
      UriComponentsBuilder uriBuilder,
      HttpServletResponse response) {

    ListCategoriesResponse listCategoriesResponse =
        getCategoryService.listActiveCategories(pageable);

    paginatedResultsRetrieved.addLinkHeaderOnPagedResourceRetrieval(
        uriBuilder, response, "/categories",
        listCategoriesResponse.getPage(),
        listCategoriesResponse.getTotalPages(),
        listCategoriesResponse.getSize());
    return ResponseEntity.ok().body(listCategoriesResponse);
  }

}
