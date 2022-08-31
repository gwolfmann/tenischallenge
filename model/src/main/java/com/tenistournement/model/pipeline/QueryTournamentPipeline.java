package com.tenistournement.model.pipeline;


import com.tenistournement.model.storageservice.MatchOperation;
import com.tenistournement.model.storageservice.TournamentOperation;
import com.tenistournement.model.tournamentModel.Match;
import com.tenistournement.model.tournamentModel.PlayerBestResultInTournament;
import com.tenistournement.model.tournamentModel.PlayerDTO;
import com.tenistournement.model.tournamentModel.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class QueryTournamentPipeline {

    @Autowired
    TournamentOperation tournamentOperation;

    private final PipelineWithBody<Tournament, List<PlayerBestResultInTournament>,List<PlayerDTO>> pipeline;

    public QueryTournamentPipeline(){
        pipeline = PipelineWithBody.<Tournament,List<PlayerBestResultInTournament>,List<PlayerDTO>>builder()
                .validateReq(Pipeline::noOperation)
                .validateBody(Pipeline::noOperation)
                .storageOp(this::getFromStorage)
                .boProcessor(this::processTournament)
                .presenter(this::responseOk)
                .build();
    }

    public Mono<ServerResponse> execute(ServerRequest serverRequest){
        return this.pipeline.execute(serverRequest);
    }

    Mono<Tournament> getFromStorage(ServerRequest serverRequest){
        return tournamentOperation.findById(serverRequest.pathVariable("tournamentId"));
    }

    Mono<List<PlayerBestResultInTournament>> processTournament(Tournament tournament){
        return Mono.just(tournament.getScores());
    }
    Mono<ServerResponse> responseOk(List<PlayerBestResultInTournament> players){
        log.info(players.toString());
        return ServerResponse.ok()
                .bodyValue(players);
    }

}
