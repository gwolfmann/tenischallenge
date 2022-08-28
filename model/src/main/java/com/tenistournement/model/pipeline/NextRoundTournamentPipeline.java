package com.tenistournement.model.pipeline;

import com.tenistournement.model.responses.Responses;
import com.tenistournement.model.storageservice.TournamentOperation;
import com.tenistournement.model.tournamentModel.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class NextRoundTournamentPipeline {

    @Autowired
    TournamentOperation tournamentOperation;

    private final Pipeline<Tournament, Tournament> pipeline;

    public NextRoundTournamentPipeline(){
        pipeline = Pipeline.<Tournament, Tournament>builder()
                .validateReq(Pipeline::noOperation)
                .validateBody(Pipeline::noOperation)
                .storageOp(this::getFromStorage)
                .boProcessor(this::processRaw)
                .presenter(this::responseOk)
                .build();
    }

    public Mono<ServerResponse> execute(ServerRequest serverRequest){
        return this.pipeline.execute(serverRequest);
    }

    Mono<Tournament> getFromStorage(ServerRequest serverRequest){
        return tournamentOperation.findById(serverRequest.pathVariable("tournamentId"));
    }

    Mono<Tournament> processRaw(Tournament tournament){
        return Mono.just(tournament);
    }
    Mono<ServerResponse> responseOk(Tournament tournament){
        if (tournament.isNull()) {
            log.info("codigo inexistente");
            return Responses.responseNotFound(tournament.getName()+" tournament inexistente");
        }
        log.info(tournament.toString());
        return ServerResponse.ok()
                .bodyValue(tournament);
    }

}
