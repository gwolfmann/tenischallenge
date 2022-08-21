package com.tenistournement.model.pipeline;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.function.Function;

@Getter
@SuperBuilder
public class PipelineWithBody<RAW,BO,BODY> {

    private BODY body;
    private Class<BODY> bodyType;
    protected final Function<ServerRequest, Mono<ServerRequest>> validateReq;
    protected final Function<ServerRequest, Mono<ServerRequest>> validateBody;
    protected final Function<ServerRequest, Mono<RAW>> storageOp;
    protected final Function<RAW, Mono<BO>> boProcessor;
    protected final Function<BO, Mono<ServerResponse>> presenter;

    public Mono<ServerResponse> execute(ServerRequest serverRequest) {
        return  Mono.just(serverRequest)
                .flatMap(validateReq)
                .flatMap(this::captureMono)
                .flatMap(validateBody)
                .flatMap(storageOp)
                .flatMap(boProcessor)
                .flatMap(presenter)
                .onErrorResume(e -> ServerResponse.ok().bodyValue(e.toString()))
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
