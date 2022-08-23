package com.tenistournement.model.storageservice;

import com.tenistournement.model.tournamentModel.Match;
import com.tenistournement.model.tournamentModel.PlayerDTO;
import com.tenistournement.model.tournamentModel.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
                .flatMap(tournament -> Mono.justOrEmpty(tournament))
                .switchIfEmpty(Mono.just(Tournament.nullTournament(id)))
                .flatMap(tournament -> Flux.from(findMatchesById(id)).collectList()
                        .flatMap(matches -> {tournament.setMatches(matches);
                        return Mono.just(tournament);}));
    }

    public Flux<Match> findMatchesById(String id) {
        return reactiveMongoTemplate.find(Query.query(
                        Criteria.where("idTournament").is(id)), Match.class);
    }

}
