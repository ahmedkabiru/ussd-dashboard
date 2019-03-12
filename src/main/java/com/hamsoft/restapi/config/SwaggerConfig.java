package com.hamsoft.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created By kabiruahmed on Mar 2019
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ussd-api-v1")
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.hamsoft.restapi"))
                .paths(regex("/api/v1.*"))
                .build()
                .apiInfo(apiEndPointsInfo());
    }
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("USSD LIBRARY")
                .description("Unstructured Supplementary Service Data(USSD) Repository board")
                .contact(new Contact("Kabiru Ahmed", "Linkedln.com/kabiruahmed", "opeyemi.kabiru@yahoo.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }

//    @Bean
//    public Docket apiV2() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("ussd-api-v2")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.hamsoft.restapi.controller"))
//                .paths(regex("/api/v2.*"))
//                .build()
//                .apiInfo(new ApiInfoBuilder()
//                        .version("2.0.0").title("USSD LIBRARY")
//                        .description("Unstructured Supplementary Service Data(USSD) Repository board")
//                        .build());
//    }

}
