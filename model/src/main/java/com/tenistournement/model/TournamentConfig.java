package com.tenistournement.model;

import com.tenistournement.model.pipeline.PlayerPipeline;
import com.tenistournement.model.tournamentModel.PlayerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TournamentConfig {

    @Bean
    public PlayerPipeline playerPipeline(){
        return new PlayerPipeline();
    }

}
