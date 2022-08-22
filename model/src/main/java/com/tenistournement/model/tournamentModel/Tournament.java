package com.tenistournement.model.tournamentModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@ToString
@JsonInclude
@Builder
@Document

public class Tournament {

        @Id
        private String idTournament;
        private PlayerDTO playerA;
        private PlayerDTO playerB;
        private Date date;
        private TenisSet[] sets;
        private Boolean absentA = false;
        private Boolean absentB = false;
        
}
