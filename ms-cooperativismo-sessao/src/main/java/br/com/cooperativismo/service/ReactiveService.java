package br.com.cooperativismo.service;

import reactor.core.CorePublisher;

public interface ReactiveService<I extends CorePublisher<?>, O extends CorePublisher<?>> {
    O processRequest(I request);
}