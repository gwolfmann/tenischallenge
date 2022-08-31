package com.tenistournement.model.pipeline;


import com.tenistournement.model.storageservice.MatchOperation;
import com.tenistournement.model.tournamentModel.Match;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class QueryMatchPipeline {

    @Autowired
    MatchOperation matchOperation;

    private final Pipeline<List<Match>, List<Match>> pipeline;

    public QueryMatchPipeline(){
        pipeline = Pipeline.<List<Match>,List<Match>>builder()
                .validateReq(Pipeline::noOperation)
                .validateBody(Pipeline::noOperation)
                .storageOp(this::getFromStorage)
                .boProcessor(Pipeline::noOperation)
                .presenter(this::responseOk)
                .build();
    }

    public Mono<ServerResponse> execute(ServerRequest serverRequest){
        return this.pipeline.execute(serverRequest);
    }

    Mono<List<Match>> getFromStorage(ServerRequest serverRequest){
        return matchOperation.findAll(serverRequest.pathVariable("playerId"));
    }

    Mono<ServerResponse> responseOk(List<Match> matches){
        log.info(matches.toString());
        return ServerResponse.ok()
                .bodyValue(matches);
    }

}
