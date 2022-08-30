package com.tenistournement.model;

import com.tenistournement.model.pipeline.*;
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
    @Bean
    public GetTournamentPipeline getTournamentPipeline(){
        return new GetTournamentPipeline();
    }
    @Bean
    public PostTournamentPipeline postTournamentPipeline(){
        return new PostTournamentPipeline();
    }
    @Bean
    public RegistrationTournamentPipeline registrationTournamentPipeline(){
        return new RegistrationTournamentPipeline();
    }

    @Bean
    public GetMatchPipeline getMatchPipeline(){  return new GetMatchPipeline();
    }
    @Bean
    public PostMatchPipeline postMatchPipeline(){
        return new PostMatchPipeline();
    }

    @Bean
    public NextRoundTournamentPipeline nextRoundTournamentPipeline(){
        return new NextRoundTournamentPipeline();
    }
    @Bean
    public SimulatePlayPipeline simulatePlayPipeline(){
        return new SimulatePlayPipeline();
    }

}
