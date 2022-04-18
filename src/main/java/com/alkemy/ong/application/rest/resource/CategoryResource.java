package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateCategoryRequest;
import com.alkemy.ong.application.rest.request.UpdateCategoryRequest;
import com.alkemy.ong.application.rest.response.CategoryResponse;
import com.alkemy.ong.application.rest.response.ErrorResponse;
import com.alkemy.ong.application.rest.response.ListCategoriesResponse;
import com.alkemy.ong.application.service.abstraction.ICreateCategoryService;
import com.alkemy.ong.application.service.abstraction.IDeleteCategoryService;
import com.alkemy.ong.application.service.abstraction.IGetCategoryService;
import com.alkemy.ong.application.service.abstraction.IUpdateCategoryService;
import com.alkemy.ong.application.util.PaginatedResultsRetrieved;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Category Endpoints", description = "Category Endpoints")
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

  @Operation(summary = "Create Category.", description = "Create a new category.", tags = "Post")
  @ApiResponses(value = {
      @ApiResponse(content = @Content(schema = @Schema(implementation = CategoryResponse.class)),
          responseCode = "201", description = "Returns category created."),
      @ApiResponse(responseCode = "400", description = "Invalid input data.", content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)
      ))
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryResponse> create(
      @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
          description = "New category to create", content = @Content(schema =
      @Schema(implementation = CreateCategoryRequest.class)))
          CreateCategoryRequest createCategoryRequest) {
    CategoryResponse categoryResponse = createCategoryService.create(createCategoryRequest);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(categoryResponse.getId())
        .toUri();

    return ResponseEntity.created(location).body(categoryResponse);
  }

  @Operation(summary = "Update Category.", description = "Update category.", tags = "Put")
  @ApiResponses(value = {
      @ApiResponse(content = @Content(schema = @Schema(implementation = CategoryResponse.class)),
          responseCode = "200", description = "Returns category updated."),
      @ApiResponse(responseCode = "400", description = "Invalid input data.", content = @Content(
          schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Category not found.", content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryResponse> update(@PathVariable Long id,
      @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters
          .RequestBody(required = true, description = "Category updated", content = @Content(
          schema = @Schema(implementation = UpdateCategoryRequest.class)))
          UpdateCategoryRequest updateCategoryRequest) {
    return ResponseEntity.ok().body(updateCategoryService.update(id, updateCategoryRequest));
  }

  @Operation(summary = "Delete category.", description = "Delete category.", tags = "Delete")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Category deleted."),
      @ApiResponse(responseCode = "404", description = "Category not found.", content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteCategoryService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Get category", description = "Get category", tags = "Get")
  @ApiResponse(responseCode = "200", description = "Returns a category", content = @Content(
      schema = @Schema(implementation = CategoryResponse.class)))
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryResponse> getBy(@PathVariable Long id) {
    return ResponseEntity.ok().body(getCategoryService.getBy(id));
  }

  @Operation(summary = "Get categories.", description = "Get actives categories.", tags = "Get")
  @ApiResponse(responseCode = "200", description = "Returns list of categories.",
      content = @Content(schema = @Schema(implementation = ListCategoriesResponse.class)))
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
