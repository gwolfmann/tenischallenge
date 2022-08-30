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

    public static MalePlayer from(PlayerDTO playerDTO){
        return MalePlayer.builder()
                .idPlayer(playerDTO.getIdPlayer())
                .name(playerDTO.getName())
                .ability(playerDTO.getAbility())
                .strong(playerDTO.getStrong())
                .velocity(playerDTO.getVelocity())
                .build();
    }

    @Override
    public Float getPerformance() {
        return (float) (getAbility() * 1.0 +
                getStrong() * 0.5 +
                getVelocity() * 0.5);
    }
}
