package com.mangofactory.swagger.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangofactory.swagger.models.dto.builder.ApiInfoBuilder;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.mangofactory.swagger.models.dto.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@EnableSwagger
@ComponentScan("com.mangofactory.swagger.dummy") //Scan some controllers
public class CustomJavaPluginConfig {

  private SpringSwaggerConfig springSwaggerConfig;

  /**
   * Required to autowire SpringSwaggerConfig
   *
   * @param springSwaggerConfig
   */
  @Autowired
  public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
    this.springSwaggerConfig = springSwaggerConfig;
  }

  /**
   * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc framework - allowing for multiple
   * swagger groups i.e. same code base multiple swagger resource listings
   */
  @Bean
  public SwaggerSpringMvcPlugin customImplementation() {
    return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
            .swaggerGroup("customPlugin")
            .includePatterns(".*pet.*");
  }

  @Bean
  public SwaggerSpringMvcPlugin secondCustomImplementation() {
    return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
            .swaggerGroup("secondCustomPlugin")
            .apiInfo(apiInfo())
            .includePatterns("/feature.*");
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  private ApiInfo apiInfo() {
    ApiInfo apiInfo = new ApiInfoBuilder().title("My Apps API Title").description("My Apps API Description")
            .termsOfServiceUrl("My Apps API terms of service").contact("My Apps API Contact Email").license("My Apps " +
                    "API Licence Type").licenseUrl("My Apps API License URL").build();
    return apiInfo;
  }
}
