package com.tenistournement.model.storageservice;

import com.tenistournement.model.responses.Responses;
import com.tenistournement.model.tournamentModel.PlayerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class PlayerOperation {
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<PlayerDTO> save(Mono<PlayerDTO> player){
        return reactiveMongoTemplate.save(player);
    }

    public Mono<PlayerDTO> findById(String id) {
        return reactiveMongoTemplate.findById(id, PlayerDTO.class)
                .flatMap(pl -> Mono.justOrEmpty(pl))
                .switchIfEmpty(Mono.just(PlayerDTO.nullPlayer(id)));
    }

}
