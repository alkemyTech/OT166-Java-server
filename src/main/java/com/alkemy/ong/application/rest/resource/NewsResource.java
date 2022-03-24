package com.alkemy.ong.application.rest.resource;

import com.alkemy.ong.application.rest.request.NewsRequest;
import com.alkemy.ong.application.rest.response.NewsResponse;
import com.alkemy.ong.application.service.abstraction.INewsService;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("news")
public class NewsResource {

  private static final String URI_NEWS = "http://localhost:8080/news/";

  @Autowired
  INewsService newsService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<NewsResponse> postNews(@Valid @RequestBody NewsRequest newsRequest)
      throws URISyntaxException {

    NewsResponse newsResponse = newsService.createNews(newsRequest);
    URI location = new URI(URI_NEWS + newsResponse.getId());

    return ResponseEntity.created(location).body(newsResponse);
  }

}
