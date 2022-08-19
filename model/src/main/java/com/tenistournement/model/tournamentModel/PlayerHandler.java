package com.tenistournement.model.tournamentModel;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PlayerHandler {

    public Mono<ServerResponse> player(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(MalePlayer.builder()
                                .idPlayer("PL01")
                                .name("Jose Raqueta")
                                .ability(1)
                                .strong(2)
                                .velocity(3)
                        .build()));
    }
}
