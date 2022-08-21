package com.tenistournement.model;

import com.tenistournement.model.pipeline.GetPlayerPipeline;
import com.tenistournement.model.pipeline.PostPlayerPipeline;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TournamentConfig {

    @Bean
    public GetPlayerPipeline getPlayerPipeline(){
        return new GetPlayerPipeline();
    }
    @Bean
    public PostPlayerPipeline postPlayerPipeline(){
        return new PostPlayerPipeline();
    }


}
