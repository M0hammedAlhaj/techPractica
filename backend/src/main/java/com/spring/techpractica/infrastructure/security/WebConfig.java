package com.spring.techpractica.infrastructure.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOriginPatterns(
                "http://51.20.190.241:*",
                "https://51.20.190.241:*"
        )
        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
        .allowedHeaders("Content-Type", "Authorization")
        .allowCredentials(false);
    }
}