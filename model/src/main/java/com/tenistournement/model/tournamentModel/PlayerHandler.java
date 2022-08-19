package com.tenistournement.model.tournamentModel;

import com.tenistournement.model.pipeline.GetPlayerPipeline;
import com.tenistournement.model.pipeline.PostPlayerPipeline;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class PlayerHandler {

    GetPlayerPipeline getPlayerPipeline;
    PostPlayerPipeline postPlayerPipeline;

    public Mono<ServerResponse> player(ServerRequest request) {
        return getPlayerPipeline.execute(request);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return postPlayerPipeline.execute(request);
    }

}
