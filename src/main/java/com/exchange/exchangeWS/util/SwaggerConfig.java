package com.exchange.exchangeWS.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.exchange.exchangeWS"))
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "CurrencyExchangeWebService",
                "API which allows you to convert currencies into Polish Zloty's, Euros, Dollars and British Pounds. "
                        + "\n\nREMEMBER: Service charges a two percent commission for EACH transaction.",
                "1.0",
                null,
                new Contact("Mateusz Samulnik", "http://linkedin.com/in/mateusz-samulnik/", "mateuszsamulnik@gmail.com"),
                null,
                null,
                Collections.emptyList());
    }
}
