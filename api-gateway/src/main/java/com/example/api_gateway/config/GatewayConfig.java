package com.example.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("book-service", r -> r.path("/bookService/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://book-service"))
                .route("student-service", r -> r.path("/studentService/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://student-service"))
                .route("order-detail", r -> r.path("/orderService/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://order-detail"))
                .build();
    }
}
