package br.com.cooperativismo.strategy;

import reactor.core.publisher.Mono;

public interface ProcessService<T, R> {
    Mono<R> process(T processable);
}