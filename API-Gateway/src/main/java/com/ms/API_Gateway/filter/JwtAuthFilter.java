package com.ms.API_Gateway.filter;

import com.ms.API_Gateway.dto.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
//@Order is an annotation in Spring used to define the execution order of components that implement the same interface or participate in a chain.
//Lower number = higher priority , means (0) > (1) > (2)  RUNS ACCORDINGLY.
@Order(1)
public class JwtAuthFilter implements GlobalFilter {

    @Autowired
    private WebClient.Builder webClientBuilder;


    //Intercepts every request.
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = exchange.getRequest().getURI().getPath();

        // 💡 Skip token validation for public routes
        // coz gateway apply filter on all routes  that's why we need to make it public routes
        if (path.contains("/auth/login") || path.contains("/auth/register")) {
            System.out.println("🔓 Skipping authentication for path: " + path);
            return chain.filter(exchange);
        }


        // Check for token
        String rawAuth = request.getHeaders().getFirst("Authorization");

        // If no token, return 401.
        if (rawAuth == null || !rawAuth.startsWith("Bearer ")) {
            return onError(exchange, "Authorization header missing", HttpStatus.UNAUTHORIZED);
        }

        String token = rawAuth.substring(7); // strip Bearer

        //Calls Auth Service’s /auth/validate.
        return webClientBuilder.build()
                .get()
                .uri("http://AUTH-SERVICE/auth/validate")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(AuthResponseDTO.class)

                .flatMap(authResponse -> {
                    // injecting role and email into header
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header("X-User-Id" , String.valueOf(authResponse.getId()))
                            .header("X-User-Email", authResponse.getEmail())
                            .header("X-User-Role", authResponse.getRoles().getFirst())
                            .build();
                    // forward the new header to downstream services(another microservice)
                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                })
                // If validation fails, return 401.
                .onErrorResume(e -> {
                    return onError(exchange, "Unauthorized", HttpStatus.UNAUTHORIZED);
                });
    }


    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
