package com.tenistournement.model.tournamentModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

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
        private PlayerDTO[] players;
        private List<Match> matches;

        public Boolean isNull(){
                return this.getIdTournament().equals(NULL_TOURNAMENT);
        }

}
