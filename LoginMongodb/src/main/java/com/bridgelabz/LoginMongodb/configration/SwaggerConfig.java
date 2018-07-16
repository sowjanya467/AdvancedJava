package com.bridgelabz.LoginMongodb.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

/*************************************************************************************************************
 *
 * purpose: swagger configuration
 *
 * @author sowjanya467
 * @version 1.0
 * @since 13-05-18
 *
 * **************************************************************************************************/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").apiInfo(apiInfo()).select()
				.paths(postPaths()).build();
	}

	private Predicate<String> postPaths() {
		return or(regex("/api/posts.*"), regex("/*/.*"));
	}

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("ToDo API").description("Google keep API reference for developers")
				.termsOfServiceUrl("http://bsajhsdj.com").contact("msowjanya2014@gmail.com").license("sowjanya License")
				.licenseUrl("msowjanya2014@gmail.com").version("1.0").build();
	}

}