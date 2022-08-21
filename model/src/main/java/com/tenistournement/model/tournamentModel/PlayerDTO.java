package com.tenistournement.model.tournamentModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@ToString
@JsonInclude
@Builder
@Document
public class PlayerDTO {
        @Id
        private String idPlayer;
        private String name;
        private Integer ability;
        private Integer strong;
        private Integer velocity;
        private Integer reaction;
        private Boolean isMale;

}
