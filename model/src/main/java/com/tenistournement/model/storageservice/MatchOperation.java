package com.tenistournement.model.storageservice;

import com.tenistournement.model.tournamentModel.Match;
import com.tenistournement.model.tournamentModel.PlayerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MatchOperation {
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Match> save(Mono<Match> match){
        return reactiveMongoTemplate.save(match);
    }

    public Mono<Match> find(String id,String playerIdA, String playerIdB) {
        final Query query = Query.query(new Criteria().andOperator(
                Criteria.where("idTournament").is(id),
                        Criteria.where("playerA.idPlayer").is(playerIdA),
                        Criteria.where("playerB.idPlayer").is(playerIdB)));
        return reactiveMongoTemplate.findOne(query, Match.class)
                .flatMap(pl -> Mono.justOrEmpty(pl))
                .switchIfEmpty(Mono.just(Match.nullMatch()));
    }

}
