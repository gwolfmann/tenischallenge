package com.tenistournement.model.pipeline;

import com.tenistournement.model.responses.Responses;
import com.tenistournement.model.storageservice.PlayerOperation;
import com.tenistournement.model.tournamentModel.MalePlayer;
import com.tenistournement.model.tournamentModel.PlayerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class PostPlayerPipeline {
    @Autowired
    PlayerOperation playerOperation;
    private final PipelineWithBody<PlayerDTO, PlayerDTO, PlayerDTO> playerPipelineWithBody;

    public PostPlayerPipeline(){
        playerPipelineWithBody = PipelineWithBody.<PlayerDTO, PlayerDTO, PlayerDTO>builder()
                .bodyType(PlayerDTO.class)
                .validateReq(this::validateHeader)
                .validateBody(this::validateBody)
                .storageOp(this::getFromStorage)
                .boProcessor(this::processRaw)
                .presenter(this::responseOk)
                .build();
    }

    public Mono<ServerResponse> execute(ServerRequest serverRequest){
        return this.playerPipelineWithBody.execute(serverRequest);
    }

    Mono<ServerRequest> validateHeader(ServerRequest serverRequest){
        if (!serverRequest.headers().header("token").get(0).equals("123")) {
            log.error("Headers Incorrecto");
            return Mono.error(new Exception("Headers incorrecto"));
        }
        return Mono.just(serverRequest);
    }

    Mono<ServerRequest> validateBody(ServerRequest serverRequest){
        PlayerDTO playerDTO = playerPipelineWithBody.getBody();
        if (null==playerDTO.getIsMale()) {
            log.error("Body Incorrecto");
            return Mono.error(new Exception("Body incorrecto"));
        }
        return Mono.just(serverRequest);
    }
    Mono<PlayerDTO> getFromStorage(ServerRequest serverRequest){
        PlayerDTO playerDTO = playerPipelineWithBody.getBody();
        if (playerDTO.getIdPlayer().equals("PL00")) {
            log.error("Player de test");
            return Mono.error(new Exception("Player de test"));
        }
        return playerOperation.save(Mono.just(testPlayer()));
    }

    Mono<PlayerDTO> processRaw(PlayerDTO player){
        return Mono.just(player);
    }
    Mono<ServerResponse> responseOk(PlayerDTO player){
        log.info(player.toString());
        return Responses.responseOk("player "+player.getName()+" incorporado");
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
