package com.alkemy.ong.infrastructure.spring.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  private static final String TITLE = "Somos más";
  private static final String DESCRIPTION = "We are a non-profit civil association that was "
      + "created in 1997 with the intention of giving food to families in the neighborhood. "
      + "Over time we got involved with the community and expanded and improved our work "
      + "capacity. We are a community center that accompanies more than 700 people through "
      + "the areas of: Education, sports, early childhood, health, food and social work.";

  @Bean
  public OpenAPI api() {
    return new OpenAPI()
        .info(new Info().title(TITLE)
            .description(DESCRIPTION)
        );
  }

}
