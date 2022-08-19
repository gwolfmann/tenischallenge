package com.tenistournement.model;

import com.tenistournement.model.greeting.GreetingHandler;
import com.tenistournement.model.tournamentModel.PlayerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class TournamentRouter {

    @Bean
    public RouterFunction<ServerResponse> greeting(GreetingHandler greetingHandler) {
        return RouterFunctions
                .route(GET("/hello").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello);
    }

    @Bean
    public RouterFunction<ServerResponse> player(PlayerHandler playerHandler) {
        return RouterFunctions
                .route(GET("/player").and(accept(MediaType.APPLICATION_JSON)), playerHandler::player);
    }
}