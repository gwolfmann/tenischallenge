package com.tenistournement.model.tournamentModel;

import com.tenistournement.model.pipeline.GetTournamentPipeline;
import com.tenistournement.model.pipeline.NextRoundTournamentPipeline;
import com.tenistournement.model.pipeline.PostTournamentPipeline;
import com.tenistournement.model.pipeline.RegistrationTournamentPipeline;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class TournamentHandler {

    GetTournamentPipeline getTournamentPipeline;
    PostTournamentPipeline postTournamentPipeline;
    RegistrationTournamentPipeline registrationTournamentPipeline;
    NextRoundTournamentPipeline nextRoundTournamentPipeline;

    public Mono<ServerResponse> tournament(ServerRequest request) {
        return getTournamentPipeline.execute(request);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return postTournamentPipeline.execute(request);
    }

    public Mono<ServerResponse> registration(ServerRequest request) {
        return registrationTournamentPipeline.execute(request);
    }

    public Mono<ServerResponse> createNextRound(ServerRequest request) {
        return nextRoundTournamentPipeline.execute(request);
    }

}
