package com.tenistournement.model.tournament;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class MalePlayer extends Player {

    private Integer strong;
    private Integer velocity;

    @Override
    public Float getPerformance() {
        return (float) (getAbility() * 1.0 +
                getStrong() * 0.5 +
                getVelocity() * 0.5);
    }
}
