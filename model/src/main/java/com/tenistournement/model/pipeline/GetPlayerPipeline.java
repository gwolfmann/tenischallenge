package com.tenistournement.model.pipeline;

import com.tenistournement.model.tournamentModel.MalePlayer;
import com.tenistournement.model.tournamentModel.PlayerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class GetPlayerPipeline {
    private final Pipeline<PlayerDTO, PlayerDTO> pipeline;

    public GetPlayerPipeline(){
        pipeline = Pipeline.<PlayerDTO, PlayerDTO>builder()
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

    Mono<PlayerDTO> getFromStorage(ServerRequest serverRequest){
        return Mono.just(this.testPlayer());
    }

    Mono<PlayerDTO> processRaw(PlayerDTO player){
        return Mono.just(player);
    }
    Mono<ServerResponse> responseOk(PlayerDTO player){
        log.info(player.toString());
        return ServerResponse.ok()
                .bodyValue(player);
    }

    private PlayerDTO testPlayer(){
        return PlayerDTO.builder()
                .idPlayer("PL01")
                .name("Jose Raqueta")
                .ability(1)
                .strong(2)
                .velocity(3)
                .isMale(Boolean.TRUE)
                .build();
    }
}
