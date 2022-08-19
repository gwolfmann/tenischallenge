package com.tenistournement.model.tournamentModel;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString(callSuper = true)
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
    /*@Override
    public String toString(){
        return super.toString()+String.valueOf(strong)+String.valueOf(velocity);
    }
*/
}
