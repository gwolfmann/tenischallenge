package com.tenistournement.model.tournamentModel;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@Builder
public class FemalePlayer implements Performance {

    private String idPlayer;
    private String name;
    private Integer ability;
    private Integer reaction;

    @Override
    public Float getPerformance() {
        return (float) (getAbility() +
                getReaction());
    }
}
