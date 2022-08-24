package com.tenistournement.model.pipeline;

import com.tenistournement.model.responses.Responses;
import com.tenistournement.model.storageservice.TournamentOperation;
import com.tenistournement.model.tournamentModel.RegistrationDTO;
import com.tenistournement.model.tournamentModel.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class RegistrationTournamentPipeline {
    @Autowired
    TournamentOperation tournamentOperation;
    private final PipelineWithBody<Tournament, Tournament, RegistrationDTO> registrationDTOPipeline;

    public RegistrationTournamentPipeline(){
        registrationDTOPipeline = PipelineWithBody.<Tournament, Tournament, RegistrationDTO>builder()
                .bodyType(RegistrationDTO.class)
                .validateReq(this::validateHeader)
                .validateBody(this::validateBody)
                .storageOp(this::getFromStorage)
                .boProcessor(this::processRaw)
                .presenter(this::responseOk)
                .build();
    }

    public Mono<ServerResponse> execute(ServerRequest serverRequest){
        return this.registrationDTOPipeline.execute(serverRequest);
    }

    Mono<ServerRequest> validateHeader(ServerRequest serverRequest){
        if (!serverRequest.headers().header("token").get(0).equals("123")) {
            log.error("Headers Incorrecto");
            return Mono.error(new Exception("Headers incorrecto"));
        }
        return Mono.just(serverRequest);
    }

    Mono<ServerRequest> validateBody(ServerRequest serverRequest){
        RegistrationDTO registrationDTO = registrationDTOPipeline.getBody();
        if (null==registrationDTO.getIdTournament()) {
            log.error("Body Incorrecto en registration");
            return Mono.error(new Exception("Body incorrecto en registration"));
        }
        return Mono.just(serverRequest);
    }
    Mono<Tournament> getFromStorage(ServerRequest serverRequest){
        RegistrationDTO registrationDTO = registrationDTOPipeline.getBody();
        return tournamentOperation.findById(registrationDTO.getIdTournament())
                .flatMap(tournament -> Mono.justOrEmpty(tournament))
                .switchIfEmpty(Mono.error(new Exception(registrationDTO.getIdTournament()+ " Inexistente")))
                .flatMap(tournament -> tournamentOperation.update(tournament,registrationDTO.getPlayers()));
    }
    Mono<Tournament> processRaw(Tournament tournament){
        return Mono.just(tournament);
    }
    Mono<ServerResponse> responseOk(Tournament tournament){
        log.info(tournament.toString());
        Integer recs = tournament.getPlayers().size();
        return Responses.responseOk("Fueron registrados "+String.valueOf(recs)+" jugadores");
    }

}
