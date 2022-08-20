package com.tenistournement.model.pipeline;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.function.Function;

@SuperBuilder
@Getter
public class PipelineWithBody<BODY,RAW,BO> extends  Pipeline{

    private BODY body;
    private final Class<BODY> bodyType;
    private final Function<ServerRequest, Mono<ServerRequest>> validateBody;
    @Override
    public Mono<ServerResponse> execute(ServerRequest serverRequest) {
        return captureMono(serverRequest)
                .flatMap(validateReq)
                .flatMap(validateBody)
                .flatMap(storageOp)
                .flatMap(boProcessor)
                .flatMap(presenter)
                .contextWrite(Context.of("key","value"));
    }

    private Mono<ServerRequest> captureMono(ServerRequest serverRequest){
        return Mono.just(serverRequest)
                .flatMap(sr->sr.bodyToMono(bodyType))
                .flatMap(body -> this.setBody(body,serverRequest));
    }
    private Mono<ServerRequest> setBody(BODY body, ServerRequest serverRequest){
        this.body=body;
        return Mono.just(serverRequest);
    }
}
