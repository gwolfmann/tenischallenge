package com.tenistournement.model.tournamentModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import java.util.List;

@Data
@ToString
@JsonInclude
@Builder
public class RegistrationDTO {

        private static String NULL_TOURNAMENT = "null";
        public static RegistrationDTO nullRegistration(String id){
                return RegistrationDTO.builder()
                        .idTournament(NULL_TOURNAMENT)
                        .build();
        }

        private String idTournament;
        private List<PlayerDTO> players;

        public Boolean isNull(){
                return this.getIdTournament().equals(NULL_TOURNAMENT);
        }

}
