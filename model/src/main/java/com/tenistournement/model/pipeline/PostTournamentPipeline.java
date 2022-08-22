package com.tenistournement.model.pipeline;

import com.tenistournement.model.responses.Responses;
import com.tenistournement.model.storageservice.PlayerOperation;
import com.tenistournement.model.storageservice.TournamentOperation;
import com.tenistournement.model.tournamentModel.PlayerDTO;
import com.tenistournement.model.tournamentModel.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class PostTournamentPipeline {
    @Autowired
    TournamentOperation tournamentOperation;
    private final PipelineWithBody<Tournament, Tournament, Tournament> tournamentPipelineWithBody;

    public PostTournamentPipeline(){
        tournamentPipelineWithBody = PipelineWithBody.<Tournament, Tournament, Tournament>builder()
                .bodyType(Tournament.class)
                .validateReq(this::validateHeader)
                .validateBody(this::validateBody)
                .storageOp(this::getFromStorage)
                .boProcessor(this::processRaw)
                .presenter(this::responseOk)
                .build();
    }

    public Mono<ServerResponse> execute(ServerRequest serverRequest){
        return this.tournamentPipelineWithBody.execute(serverRequest);
    }

    Mono<ServerRequest> validateHeader(ServerRequest serverRequest){
        if (!serverRequest.headers().header("token").get(0).equals("123")) {
            log.error("Headers Incorrecto");
            return Mono.error(new Exception("Headers incorrecto"));
        }
        return Mono.just(serverRequest);
    }

    Mono<ServerRequest> validateBody(ServerRequest serverRequest){
        Tournament tournament = tournamentPipelineWithBody.getBody();
        if (null==tournament.getIdTournament()) {
            log.error("Body Incorrecto");
            return Mono.error(new Exception("Body incorrecto"));
        }
        return Mono.just(serverRequest);
    }
    Mono<Tournament> getFromStorage(ServerRequest serverRequest){
        Tournament tournament = tournamentPipelineWithBody.getBody();
        return tournamentOperation.save(Mono.just(tournament));
    }

    Mono<Tournament> processRaw(Tournament tournament){
        return Mono.just(tournament);
    }
    Mono<ServerResponse> responseOk(Tournament tournament){
        log.info(tournament.toString());
        return Responses.responseOk("player "+tournament.getName()+" incorporado");
    }

}
