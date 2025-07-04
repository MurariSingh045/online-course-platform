package com.ms.API_Gateway.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }



    // This bean would automatically use by Spring Boot
    // providing routes of the
    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                // route for COURSE-SERVICE
                .route("course-api", r -> r.path("/courses/**")
                        .uri("lb://COURSE-SERVICE"))

                // route for AUTH-SERVICE
                .route("auth-api", r -> r
                        .path("/auth/**")
                        .uri("lb://AUTH-SERVICE"))

                // rote for  ENROLLMENT-SERVICE
                .route("enrollment-api" , r -> r
                        .path("/enrollments/**")
                        .uri("lb://ENROLLMENT-SERVICE"))

                .build();
    }



}
