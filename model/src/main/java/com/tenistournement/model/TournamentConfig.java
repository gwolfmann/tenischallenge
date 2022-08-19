package com.tenistournement.model;

import com.tenistournement.model.pipeline.GetPlayerPipeline;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TournamentConfig {

    @Bean
    public GetPlayerPipeline playerPipeline(){
        return new GetPlayerPipeline();
    }

}
