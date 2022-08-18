package com.tenistournement.model.tournament;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class Player implements Performance{

        private String idPlayer;
        private String name;
        private Integer ability;

        public abstract Float getPerformance();
}
