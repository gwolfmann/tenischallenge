package com.tenistournement.model.tournamentModel;

import lombok.Data;

import java.util.Date;

@Data
public class Tournament {

        private String idTournament;
        private Player playerA;
        private Player playerB;
        private Date date;
        private TenisSet[] sets;
        private Boolean absentA = false;
        private Boolean absentB = false;
        
}
