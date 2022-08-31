package com.tenistournement.model.tournamentModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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
        public Tournament generateNextRound(Boolean isMale){
                Integer maxLevelPresent = this.getMaxLevelPlayed(isMale);
                List<PlayerDTO> winners = new ArrayList<>();
                if (maxLevelPresent==0) {
                    winners = this.getPlayers().stream()
                            .filter(playerDTO -> playerDTO.getIsMale().equals(isMale))
                            .toList();
                } else {
                    winners = this.winnerOfLevel(maxLevelPresent,isMale);
                }
                this.matches.addAll(this.defineMatches(winners,maxLevelPresent));
                return this;
        }

        public Boolean hasUnplayedMatches(Boolean isMale){
                AtomicReference<Boolean> result = new AtomicReference<>(false);
                matches.stream()
                        .filter(match -> match.isMale()==isMale)
                        .forEach(match -> result.set(result.get() || !match.played()));
                return result.get();
        }

        public Tournament simulatePlay(Boolean isMale){
                this.matches.stream()
                        .filter(match -> match.isMale()==isMale && !match.played())
                        .forEach(Match::simulatePlay);
                return this;
        }

        private Integer getMaxLevelPlayed(Boolean isMale){
                return matches.stream()
                        .filter(match -> match.isMale()==isMale)
                        .map(match -> match.played() ? match.getRound() : 0)
                        .reduce(0,(a,b)-> a > b? a :b);
        }

        private List<PlayerDTO> winnerOfLevel(Integer level,Boolean isMale){
                return matches.stream()
                        .filter(match -> match.getRound().equals(level))
                        .map(Match::winner)
                        .filter(pl -> pl.getIsMale().equals(isMale))
                        .toList();
        }

        private List<Match> defineMatches(List<PlayerDTO> players, Integer level) throws IllegalStateException {
                if (!(players.size()>0 && players.size()%2==0)) {
                        throw new IllegalStateException("El torneo tiene "+
                                players.size() +" players en disputa");
                }
                Integer qOfPlayers = players.size()/2;
                Date dayOfMatch = Date.from(this.initialDate.toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime()
                        .plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
                List<Match> result = new ArrayList<>();
                List<List<PlayerDTO>> listOfPlayers = players.stream().collect(Collectors
                        .partitioningBy(p -> players.indexOf(p)<qOfPlayers))
                        .values()
                        .stream().toList();
                for (int i =0 ;i< qOfPlayers; i++) {
                        result.add(Match.builder()
                                .idTournament(this.idTournament)
                                .date(dayOfMatch)
                                .playerA(listOfPlayers.get(0).get(i))
                                .playerB(listOfPlayers.get(1).get(qOfPlayers-1-i))
                                .round(level+1)
                                .sets(new ArrayList<>())
                                .absentA(false)
                                .absentB(false)
                                .build());
                }
                return result;
        }

        public List<PlayerBestResultInTournament> getScores(){
                Map<String, PlayerBestResultInTournament> map = new HashMap<>();
                getMatches().stream()
                        .sorted(Comparator.comparing(Match::getRound))  //match sort by round
                        .forEach(m->{
                                map.put(m.getPlayerA().getIdPlayer(),  //insert bestResult for player A
                                PlayerBestResultInTournament
                                .builder()
                                        .idPlayer(m.getPlayerA().getIdPlayer())
                                        .name(m.getPlayerA().getName())
                                        .round(m.getRound())
                                        .win(m.winner().equals(m.getPlayerA()))
                                        .build());
                                map.put(m.getPlayerB().getIdPlayer(), //insert bestResult for player B
                                        PlayerBestResultInTournament
                                                .builder()
                                                .idPlayer(m.getPlayerB().getIdPlayer())
                                                .name(m.getPlayerB().getName())
                                                .round(m.getRound())
                                                .win(m.winner().equals(m.getPlayerB()))
                                                .build());
                        });  //last inserted value for a player is it's best result
                return  map.entrySet().stream() //sort the maps entries by round and win
                        .map(Map.Entry::getValue)
                        .sorted(Comparator.comparing(PlayerBestResultInTournament::getRound)
                                .thenComparing(Comparator.comparing(PlayerBestResultInTournament::getWin)))
                        .toList();
        }

}
