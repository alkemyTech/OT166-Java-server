package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.CreateNewsRequest;
import com.alkemy.ong.application.rest.request.UpdateNewsRequest;
import com.alkemy.ong.application.rest.response.ListNewsResponse;
import com.alkemy.ong.application.rest.response.NewsResponse;
import com.alkemy.ong.application.service.abstraction.ICreateNewsService;
import com.alkemy.ong.application.service.abstraction.IDeleteNewsService;
import com.alkemy.ong.application.service.abstraction.IGetNewsService;
import com.alkemy.ong.application.service.abstraction.IUpdateNewsService;
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
@RequestMapping("news")
public class NewsResource {

  @Autowired
  private ICreateNewsService createNewsService;

  @Autowired
  private IUpdateNewsService updateNewsService;

  @Autowired
  private IDeleteNewsService deleteNewsService;

  @Autowired
  private IGetNewsService getNewsService;

  @Autowired
  private PaginatedResultsRetrieved paginatedResultsRetrieved;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<NewsResponse> create(
      @Valid @RequestBody CreateNewsRequest createNewsRequest) {
    NewsResponse newsResponse = createNewsService.create(createNewsRequest);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(newsResponse.getId())
        .toUri();

    return ResponseEntity.created(location).body(newsResponse);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteNewsService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<NewsResponse> update(@PathVariable Long id,
      @Valid @RequestBody UpdateNewsRequest updateNewsRequest) {
    return ResponseEntity.ok(updateNewsService.update(id, updateNewsRequest));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListNewsResponse> list(Pageable pageable,
      UriComponentsBuilder uriBuilder,
      HttpServletResponse response) {

    ListNewsResponse listNewsResponse = getNewsService.findAll(pageable);

    paginatedResultsRetrieved.addLinkHeaderOnPagedResourceRetrieval(
        uriBuilder, response,"/news",
        listNewsResponse.getPage(),
        listNewsResponse.getTotalPages(),
        listNewsResponse.getSize());

    return ResponseEntity.ok().body(listNewsResponse);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<NewsResponse> getBy(@PathVariable Long id) {
    return ResponseEntity.ok().body(getNewsService.getBy(id));
  }

}
