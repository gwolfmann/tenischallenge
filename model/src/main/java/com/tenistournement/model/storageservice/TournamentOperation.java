package com.tenistournement.model.storageservice;

import com.tenistournement.model.tournamentModel.PlayerDTO;
import com.tenistournement.model.tournamentModel.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TournamentOperation {
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Tournament> save(Mono<Tournament> tournament){
        return reactiveMongoTemplate.save(tournament);
    }

    public Mono<Tournament> findById(String id) {
        return reactiveMongoTemplate.findById(id, Tournament.class)
                .flatMap(pl -> Mono.justOrEmpty(pl))
                .switchIfEmpty(Mono.just(Tournament.nullTournament(id)));
    }

}
