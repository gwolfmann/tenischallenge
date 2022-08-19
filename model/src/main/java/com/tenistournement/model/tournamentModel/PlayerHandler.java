package com.tenistournement.model.tournamentModel;

import com.tenistournement.model.pipeline.PlayerPipeline;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class PlayerHandler {

    PlayerPipeline playerPipeline;

    public Mono<ServerResponse> player(ServerRequest request) {
        return playerPipeline.execute(request);
/*
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(MalePlayer.builder()
                                .idPlayer("PL01")
                                .name("Jose Raqueta")
                                .ability(1)
                                .strong(2)
                                .velocity(3)
                        .build()));

 */
    }
}
