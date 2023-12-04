package ru.otus.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator implements GatewayFilter {

    public static final List<String> OPEN_ENDPOINTS = List.of(
            "/auth/login",
            "/auth/validate"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isSecured.test(exchange.getRequest())) {
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }



    public Predicate<ServerHttpRequest> isSecured =
            request -> OPEN_ENDPOINTS.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
