package com.alkemy.ong.bigtest.comment;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.bigtest.util.BigTest;
import com.alkemy.ong.infrastructure.database.entity.CommentEntity;
import java.util.Optional;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class DeleteCommentIntegrationTest extends BigTest {

  @Test
  public void shouldDeleteCommentWhenRequestUserHasAdminRole() throws Exception {
    Long id = saveComment().getId();

    mockMvc.perform(delete("/comments/{id}", String.valueOf(id))
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(status().isNoContent());

    assertCommentHasBeenDeleted(id);
  }


  private void assertCommentHasBeenDeleted(Long id) {
    Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(id);
    assertTrue(optionalCommentEntity.isEmpty());
  }

}
