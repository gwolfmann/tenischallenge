package com.tenistournement.model.tournamentModel;

import com.tenistournement.model.pipeline.QueryMatchPipeline;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class QueryHandler {

    QueryMatchPipeline queryMatchPipeline;


    public Mono<ServerResponse> matchesByPlayer(ServerRequest request) {
        return queryMatchPipeline.execute(request);
    }

}
