package com.tenistournement.model.pipeline;

import com.tenistournement.model.responses.Responses;
import com.tenistournement.model.storageservice.MatchOperation;
import com.tenistournement.model.tournamentModel.Match;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class PostMatchPipeline {
    @Autowired
    MatchOperation matchOperation;
    private final PipelineWithBody<Match, Match, Match> matchPipelineWithBody;

    public PostMatchPipeline(){
        matchPipelineWithBody = PipelineWithBody.<Match,Match,Match>builder()
                .bodyType(Match.class)
                .validateReq(this::validateHeader)
                .validateBody(this::validateBody)
                .storageOp(this::storageProcessing)
                .boProcessor(this::processRaw)
                .presenter(this::responseOk)
                .build();
    }

    public Mono<ServerResponse> execute(ServerRequest serverRequest){
        return this.matchPipelineWithBody.execute(serverRequest);
    }

    Mono<ServerRequest> validateHeader(ServerRequest serverRequest){
        if (!serverRequest.headers().header("token").get(0).equals("123")) {
            log.error("Headers Incorrecto");
            return Mono.error(new Exception("Headers incorrecto"));
        }
        return Mono.just(serverRequest);
    }

    Mono<ServerRequest> validateBody(ServerRequest serverRequest){
        Match match = matchPipelineWithBody.getBody();
       /* if (null==match.getIsMale()) {
            log.error("Body Incorrecto");
            return Mono.error(new Exception("Body incorrecto"));
        }

        */
        return Mono.just(serverRequest);
    }
    Mono<Match> storageProcessing(ServerRequest serverRequest){
        Match match = matchPipelineWithBody.getBody();
        return matchOperation.save(Mono.just(match));
    }

    Mono<Match> processRaw(Match match){
        return Mono.just(match);
    }
    Mono<ServerResponse> responseOk(Match match){
        log.info(match.toString());
        return Responses.responseOk("match "+match.toString()+" incorporado");
    }

}
