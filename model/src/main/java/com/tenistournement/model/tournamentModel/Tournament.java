package com.tenistournement.model.tournamentModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@JsonInclude
@Builder
@Document
public class Tournament {

        private static String NULL_TOURNAMENT = "null";
        public static Tournament nullTournament(String id){
                return Tournament.builder()
                        .idTournament(NULL_TOURNAMENT)
                        .name(id)
                        .build();
        }

        @Id
        private String idTournament;
        private String name;
        private Date initialDate;
        private Date finalDate;
        private List<PlayerDTO> players;
        private List<Match> matches;

        public Boolean isNull(){
                return this.getIdTournament().equals(NULL_TOURNAMENT);
        }

        public List<PlayerDTO> addPlayers(List<PlayerDTO> newPlayers) {
                getPlayers().addAll(newPlayers);
                return getPlayers();
        }
        public void defineNextLevelMatches(){
                Integer maxLevelPresent = this.getMaxLevelPlayed();
                List<PlayerDTO> winners = this.winnerOfLevel(maxLevelPresent);
                this.matches.addAll(this.defineMatches(winners,maxLevelPresent));
        }

        private Integer getMaxLevelPlayed(){
                return matches.stream()
                        .map(match -> match.played() ? match.getRound() : -1)
                        .reduce(0,(a,b)-> a > b? a :b);
        }

        private List<PlayerDTO> winnerOfLevel(Integer level){
                return matches.stream()
                        .filter(match -> match.getRound() == level)
                        .map(Match::winner)
                        .toList();
        }

        private List<Match> defineMatches(List<PlayerDTO> players, Integer level){
                assert (players.size()>0 && players.size()%2==0);
                Integer qOfPlayers = players.size()/2;
                Date dayOfMatch = Date.from(this.initialDate.toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime()
                        .plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
                List<Match> result = new ArrayList<>();
                List<List<PlayerDTO>> listOfPlayers = players.stream().collect(Collectors
                        .partitioningBy(p -> players.indexOf(p)>qOfPlayers))
                        .values()
                        .stream().toList();
                for (int i =0 ;i< qOfPlayers; i++) {
                        result.add(Match.builder()
                                .idTournament(this.idTournament)
                                .date(dayOfMatch)
                                .playerA(listOfPlayers.get(0).get(i))
                                .playerB(listOfPlayers.get(1).get(qOfPlayers-1-i))
                                .round(level+1)
                                .build());
                }
                return result;
        }

}
