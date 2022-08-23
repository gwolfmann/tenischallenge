package com.tenistournement.model.tournamentModel;

import com.tenistournement.model.pipeline.GetMatchPipeline;
import com.tenistournement.model.pipeline.GetPlayerPipeline;
import com.tenistournement.model.pipeline.PostMatchPipeline;
import com.tenistournement.model.pipeline.PostPlayerPipeline;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class MatchHandler {

    GetMatchPipeline getMatchPipeline;
    PostMatchPipeline postMatchPipeline;

    public Mono<ServerResponse> match(ServerRequest request) {
        return getMatchPipeline.execute(request);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return postMatchPipeline.execute(request);
    }

}
