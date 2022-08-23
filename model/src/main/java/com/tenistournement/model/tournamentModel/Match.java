package com.tenistournement.model.tournamentModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


@Data
@ToString
@JsonInclude
@Builder
@Document
public class Match {

    private static String NULL_MATCH = "null";
    public static Match nullMatch(){
        return Match.builder()
                .idTournament(NULL_MATCH)
                .build();
    }


    private String idTournament;

    private PlayerDTO playerA;

    private PlayerDTO playerB;
    private Date date;
    private TenisSet[] sets;
    private Boolean absentA = false;
    private Boolean absentB = false;

    public PlayerDTO winner(){
        AtomicInteger setsA=new AtomicInteger(0);
        AtomicInteger setsB=new AtomicInteger(0);

        if (absentA) return playerB;
        if (absentB) return playerA;

        for (TenisSet s : sets) {
            if (s.getGamesA() > s.getGamesB()) {
                setsA.getAndIncrement();
            } else {
                setsB.getAndIncrement();
            }
        }
        if (setsA.get()>setsB.get()) {
            return playerA;
        }
        return playerB;
    }

    public Boolean isNull(){
        return this.getIdTournament().equals(NULL_MATCH);
    }
}
