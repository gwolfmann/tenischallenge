package com.tenistournement.model.responses;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class Responses {
    public static Mono<ServerResponse> responseOk(String mess){
        return ServerResponse.ok().bodyValue("Accion exitosa:"+mess);
    }
    public static Mono<ServerResponse> responseInvalida(String mess){
        return ServerResponse.ok().bodyValue("Accion no ejecutada:"+mess);
    }

    public static Mono<ServerResponse> responseNotFound(String mess){
        return ServerResponse.ok().bodyValue("No encontrado:"+mess);
    }
}
