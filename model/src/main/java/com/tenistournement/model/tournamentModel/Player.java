package com.tenistournement.model.tournamentModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@JsonInclude
@SuperBuilder
public abstract class Player implements Performance{

        private String idPlayer;
        private String name;
        private Integer ability;

        public abstract Float getPerformance();
}
