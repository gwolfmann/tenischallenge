package com.tenistournement.model.tournamentModel;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@Builder
public class MalePlayer implements Performance {

    private String idPlayer;
    private String name;
    private Integer ability;
    private Integer strong;
    private Integer velocity;

    @Override
    public Float getPerformance() {
        return (float) (getAbility() * 1.0 +
                getStrong() * 0.5 +
                getVelocity() * 0.5);
    }
}
