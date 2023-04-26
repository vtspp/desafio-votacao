package br.com.cooperativismo.service;

import reactor.core.CorePublisher;

public interface OrchestratorReactiveService<I extends CorePublisher<?>, O extends CorePublisher<?>> {
    O processRequest(I request);
}