package com.tenistournement.model.tournament;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class FemalePlayer extends Player {

    private Integer reaction;

    @Override
    public Float getPerformance() {
        return (float) (getAbility() +
                getReaction());
    }
}
