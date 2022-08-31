package com.tenistournement.model.tournamentModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@ToString
@JsonInclude
@Builder
public class PlayerBestResultInTournament {

        private String idPlayer;
        private String name;
        private Integer round;
        private Boolean win;
}
