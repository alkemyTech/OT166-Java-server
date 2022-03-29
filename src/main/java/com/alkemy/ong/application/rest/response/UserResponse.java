package com.alkemy.ong.application.rest.response;

import com.alkemy.ong.infrastructure.database.entity.RoleEntity;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String photo;
  private RoleEntity role;
  private Boolean softDeleted;
  private Timestamp createTimestamp;
}
