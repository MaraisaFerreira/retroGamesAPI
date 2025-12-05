package com.study.mf.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocsConfig {

    @Bean
    OpenAPI swaggerDocsConfigurator(){
        return new OpenAPI().info(
            new Info()
                .title("Retro Games Library API")
                .description("Management of retro games")
                .version("v1")
                .license(new License().name("Apache 2.0"))
        );
    }
}
