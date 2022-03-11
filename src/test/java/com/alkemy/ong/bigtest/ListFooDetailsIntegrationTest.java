package com.alkemy.ong.bigtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.alkemy.ong.application.rest.response.FooResponse;
import com.alkemy.ong.application.rest.response.ListFooResponse;
import com.alkemy.ong.infrastructure.database.entity.FooEntity;
import com.alkemy.ong.infrastructure.database.repository.IFooRepository;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListFooDetailsIntegrationTest {

  private TestRestTemplate restTemplate = new TestRestTemplate();
  private HttpHeaders headers = new HttpHeaders();

  @LocalServerPort
  private int port;

  @MockBean
  protected IFooRepository fooRepository;

  @Test
  public void shouldReturnOkWhenAccessedWithRoleUser() {
    when(fooRepository.findAll()).thenReturn(buildFooStubs());

    ResponseEntity<ListFooResponse> response = restTemplate.exchange(
        createURLWithPort("/foo"),
        HttpMethod.GET,
        new HttpEntity<>(headers),
        ListFooResponse.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());

    List<FooResponse> fooResponses = response.getBody().getFooes();
    assertNotNull(fooResponses);
    assertEquals(1, fooResponses.size());
    assertEquals("my foo", fooResponses.get(0).getFoo());
  }

  private List<FooEntity> buildFooStubs() {
    return List.of(new FooEntity(1L, "my foo"));
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }

}
