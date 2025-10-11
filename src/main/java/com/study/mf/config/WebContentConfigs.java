package com.study.mf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebContentConfigs implements WebMvcConfigurer {

    @Value("${cors.originPatterns}")
    private String originPatterns;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = originPatterns.split(",");

        registry.addMapping("/**")
            .allowedOrigins(origins);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML)
            .mediaType("yaml", MediaType.APPLICATION_YAML);
    }
}
