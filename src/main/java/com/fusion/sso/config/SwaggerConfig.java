package com.fusion.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.fusionAuth.controller")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("FusionAuth Implementation").description(
				"Fusion Auth is a security token that provides secure authentication and authorization for your application. It uses a combination of factors, such as username, password, and a unique token, to verify the user's identity and grant access to the requested resources. By using Fusion Auth, you can improve the security of your application and protect sensitive data from unauthorized access.  "
						+ "\n\n To Obtain Sample Key, Tenant Id, Application Id, Visit https://sandbox.fusionauth.io/admin/")
				.version("1.0.0").build();
	}
}
