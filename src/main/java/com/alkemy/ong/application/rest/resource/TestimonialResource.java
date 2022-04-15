package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateTestimonialRequest;
import com.alkemy.ong.application.rest.request.UpdateTestimonialRequest;
import com.alkemy.ong.application.rest.response.ErrorResponse;
import com.alkemy.ong.application.rest.response.ListTestimonialsResponse;
import com.alkemy.ong.application.rest.response.TestimonialResponse;
import com.alkemy.ong.application.service.abstraction.ICreateTestimonialService;
import com.alkemy.ong.application.service.abstraction.IDeleteTestimonialService;
import com.alkemy.ong.application.service.abstraction.IGetTestimonialService;
import com.alkemy.ong.application.service.abstraction.IUpdateTestimonialService;
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

@Tag(name = "Testimonial Endpoints", description = "Testimonial Endpoints")
@RestController
@RequestMapping("/testimonials")
public class TestimonialResource {

  @Autowired
  private IDeleteTestimonialService deleteTestimonialService;

  @Autowired
  private ICreateTestimonialService createTestimonialService;

  @Autowired
  private IUpdateTestimonialService updateTestimonialService;

  @Autowired
  private PaginatedResultsRetrieved paginatedResultsRetrieved;

  @Autowired
  private IGetTestimonialService getTestimonialService;


  @Operation(summary = "Delete Testimonial.", description = "Delete testimonial.", tags = "Delete")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Testimonial deleted."),
      @ApiResponse(responseCode = "404", description = "Testimonial not found.",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteTestimonialService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Create Testimonial.", description = "Create a new testimonial.",
      tags = "Post")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Returns testimonial created.",
          content = @Content(schema = @Schema(implementation = TestimonialResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data.",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TestimonialResponse> create(
      @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
          description = "New testimonial to create", content = @Content(
          schema = @Schema(implementation = CreateTestimonialRequest.class)))
          CreateTestimonialRequest createTestimonialRequest) {
    TestimonialResponse testimonialResponse = createTestimonialService.create(
        createTestimonialRequest);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(testimonialResponse.getId())
        .toUri();

    return ResponseEntity.created(location).body(testimonialResponse);

  }

  @Operation(summary = "Update Testimonial.", description = "Update testimonial", tags = "Put")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Returns testimonial update.", content =
      @Content(schema = @Schema(implementation = TestimonialResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data.", content =
      @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Testimonial not found.", content =
      @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TestimonialResponse> update(
      @PathVariable Long id, @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters
      .RequestBody(required = true, description = "Testimonial Update", content = @Content(
      schema = @Schema(implementation = UpdateTestimonialRequest.class)))
      UpdateTestimonialRequest updateTestimonialRequest) {
    return ResponseEntity.ok().body(updateTestimonialService.update(id, updateTestimonialRequest));
  }

  @Operation(summary = "Get testimonials.", description = "Get actives testimonials.", tags = "Get")
  @ApiResponse(responseCode = "200", description = "Returns list of testimonials", content =
  @Content(schema = @Schema(implementation = TestimonialResponse.class)))
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListTestimonialsResponse> list(Pageable pageable,
      UriComponentsBuilder uriBuilder, HttpServletResponse response) {
    ListTestimonialsResponse testimonialsResponse = getTestimonialService.list(pageable);
    paginatedResultsRetrieved.addLinkHeaderOnPagedResourceRetrieval(
        uriBuilder, response, "/news",
        testimonialsResponse.getPage(),
        testimonialsResponse.getTotalPages(),
        testimonialsResponse.getSize());
    return ResponseEntity.ok().body(testimonialsResponse);
  }
}
