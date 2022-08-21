package com.tenistournement.model.tournamentModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@JsonInclude
@Builder
public class PlayerDTO {

        private String idPlayer;
        private String name;
        private Integer ability;
        private Integer strong;
        private Integer velocity;
        private Integer reaction;
        private Boolean isMale;

}
