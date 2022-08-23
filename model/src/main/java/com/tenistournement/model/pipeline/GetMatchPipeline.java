package com.tenistournement.model.pipeline;

import com.tenistournement.model.responses.Responses;
import com.tenistournement.model.storageservice.MatchOperation;
import com.tenistournement.model.storageservice.PlayerOperation;
import com.tenistournement.model.tournamentModel.Match;
import com.tenistournement.model.tournamentModel.PlayerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class GetMatchPipeline {

    @Autowired
    MatchOperation matchOperation;

    private final Pipeline<Match, Match> pipeline;

    public GetMatchPipeline(){
        pipeline = Pipeline.<Match,Match>builder()
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

    Mono<Match> getFromStorage(ServerRequest serverRequest){
        return matchOperation.find(serverRequest.pathVariable("tournamentId"),
                serverRequest.pathVariable("playerIdA"),
                serverRequest.pathVariable("playerIdB"));
    }

    Mono<Match> processRaw(Match match){
        return Mono.just(match);
    }
    Mono<ServerResponse> responseOk(Match match){
        if (match.isNull()) {
            log.info("match inexistente");
            return Responses.responseNotFound(match.toString()+" match inexistente");
        }
        log.info(match.toString());
        return ServerResponse.ok()
                .bodyValue(match);
    }

}
