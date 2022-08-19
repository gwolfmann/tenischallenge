package com.tenistournement.model.tournamentModel;

import lombok.Data;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Match {

    private String idTournament;
    private Player playerA;
    private Player playerB;
    private Date date;
    private TenisSet[] sets;
    private Boolean absentA = false;
    private Boolean absentB = false;

    public Player winner(){
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
}
