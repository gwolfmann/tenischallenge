package com.tenistournement.model.pipeline;

import lombok.Builder;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.function.Function;

@Builder
public class Pipeline<RAW,BO> {

    public static <BO> Mono<BO> noOperation(BO bo) {return Mono.just(bo);}

    private final Function<ServerRequest, Mono<ServerRequest>> validateReq;
    private final Function<ServerRequest, Mono<ServerRequest>> validateBody;
    private final Function<ServerRequest, Mono<RAW>> storageOp;
    private final Function<RAW, Mono<BO>> boProcessor;
    private final Function<BO, Mono<ServerResponse>> presenter;

    public Mono<ServerResponse> execute(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .flatMap(validateReq)
                .flatMap(validateBody)
                .flatMap(storageOp)
                .flatMap(boProcessor)
                .flatMap(presenter)
                .contextWrite(Context.of("key","value"));
    }


}
