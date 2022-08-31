package com.tenistournement.model.pipeline;

import com.tenistournement.model.responses.Responses;
import com.tenistournement.model.storageservice.MatchOperation;
import com.tenistournement.model.storageservice.TournamentOperation;
import com.tenistournement.model.tournamentModel.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class SimulatePlayPipeline {

    @Autowired
    TournamentOperation tournamentOperation;
    @Autowired
    MatchOperation matchOperation;

    private final Pipeline<Tournament, Tournament> pipeline;

    public SimulatePlayPipeline(){
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
        String maleParam = serverRequest.queryParams().getFirst("male");
        if (!(maleParam.equalsIgnoreCase("true") || maleParam.equalsIgnoreCase("false"))) {
            return Mono.error(new IllegalStateException("Parameter male must be true or false"));
        }
        Boolean isMale = Boolean.valueOf(maleParam);
        return tournamentOperation.findById(serverRequest.pathVariable("tournamentId"))
                .flatMap(tournament -> {
                    if (!tournament.hasUnplayedMatches(isMale))
                        return Mono.error(new IllegalStateException("El torneo no tiene matches pendientes"));
                    else
                        return Mono.just(tournament.simulatePlay(isMale));
                });
    }

    Mono<Tournament> processRaw(Tournament tournament){
        return matchOperation.saveAll(tournament);
    }

    Mono<ServerResponse> responseOk(Tournament tournament){
        if (tournament.isNull()) {
            log.info("codigo inexistente");
            return Responses.responseNotFound(tournament.getName()+" tournament inexistente");
        }
        log.info(tournament.toString());
        return ServerResponse.ok()
                .bodyValue(tournament.getMatches());
    }

}
