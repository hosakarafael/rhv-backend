package com.rafaelhosaka.gateway.routes;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute(){
        return GatewayRouterFunctions
                .route("user_service")
                .route(
                        RequestPredicates.path("/api/user/**"),
                        HandlerFunctions.http("http://localhost:8082")
                ).build();
    }

    @Bean
    public RouterFunction<ServerResponse> videoServiceRoute(){
        return GatewayRouterFunctions
                .route("video_service")
                .route(
                        RequestPredicates.path("/api/video/**"),
                        HandlerFunctions.http("http://localhost:8081")
                ).build();
    }
}
