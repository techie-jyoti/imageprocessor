package com.example.imageprocessor.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	

	private ApiInfo apiInfo() {
		return new ApiInfo("Image service  API",	
	  "APIs for managing images.",
	  "1.0",
	  " ",
	  new Contact("jyoti nagwani",
	  " ", "jyoti@xyzsdomain.com"),
	  " ",
	  " ",
	  Collections.emptyList()); 
	}
	 

	@Bean
	public Docket api() {
	
		return new Docket(DocumentationType.OAS_30)
				.select()
		        .apis(RequestHandlerSelectors.any())
		        .paths(PathSelectors.any())
		        .build();
	}

	
}
