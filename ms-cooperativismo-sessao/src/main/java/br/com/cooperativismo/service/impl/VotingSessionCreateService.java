package br.com.cooperativismo.service.impl;

import br.com.cooperativismo.dto.VotingSessionRequest;
import br.com.cooperativismo.dto.VotingSessionResponse;
import br.com.cooperativismo.helper.ConvertHelper;
import br.com.cooperativismo.repository.VotingSessionRepository;
import br.com.cooperativismo.service.OrchestratorReactiveService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public record VotingSessionCreateService(VotingSessionRepository repository)
        implements OrchestratorReactiveService<Mono<VotingSessionRequest>, Mono<VotingSessionResponse>> {

    @Override
    public Mono<VotingSessionResponse> processRequest(Mono<VotingSessionRequest> request) {
        return request.flatMap(r -> repository.save(ConvertHelper.of(r)))
                .doOnError(Mono::error)
                .map(m -> new VotingSessionResponse(m.id(), m.sessionStatus(), null));
    }
}