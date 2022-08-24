package com.tenistournement.model;

import com.tenistournement.model.greeting.GreetingHandler;
import com.tenistournement.model.tournamentModel.MatchHandler;
import com.tenistournement.model.tournamentModel.PlayerHandler;
import com.tenistournement.model.tournamentModel.TournamentHandler;
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
                .route(GET("/hello").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello)
                .andRoute(GET("/ping").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello);
    }

    @Bean
    public RouterFunction<ServerResponse> playerRoutes(PlayerHandler playerHandler) {
        return RouterFunctions
                .route(GET("/player/{playerId}").and(accept(MediaType.APPLICATION_JSON)), playerHandler::player)
                .andRoute(POST("/player").and(accept(MediaType.APPLICATION_JSON)),playerHandler::create);
    }

    @Bean
    public RouterFunction<ServerResponse> tournamentRoutes(TournamentHandler tournamentHandler) {
        return RouterFunctions
                .route(GET("/tournament/{tournamentId}").and(accept(MediaType.APPLICATION_JSON)), tournamentHandler::tournament)
                .andRoute(POST("/tournament").and(accept(MediaType.APPLICATION_JSON)),tournamentHandler::create)
                .andRoute(POST("/registration/{tournamentId}").and(accept(MediaType.APPLICATION_JSON)),tournamentHandler::registration)
                ;
    }

    @Bean
    public RouterFunction<ServerResponse> matchRoutes(MatchHandler matchHandler) {
        return RouterFunctions
                .route(GET("/match/{tournamentId}{playerIdA}{playerIdB}").and(accept(MediaType.APPLICATION_JSON)), matchHandler::match)
                .andRoute(POST("/match").and(accept(MediaType.APPLICATION_JSON)),matchHandler::create);
    }
}