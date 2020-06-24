package com.takeaway.gameofthree.player.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configuration class allowing to define Swagger API for the project
 *
 * @author Bharadwaj Adepu
 */
@Configuration
public class SwaggerConfiguration {

    private static final String DEFAULT_REST_PACKAGE = "com.takeaway.gameofthree.player.rest";

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public Docket gameApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(this.applicationName)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(DEFAULT_REST_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Game Of Three Player API")
                .description("REST API Spec for GameOfThree service")
                .contact(new Contact("Bharadwaj Adepu", "https://github.com/bharadwajap", "bharadwajadepu@gmail.com"))
                .version("1.0") 
                .build();
    }
}
