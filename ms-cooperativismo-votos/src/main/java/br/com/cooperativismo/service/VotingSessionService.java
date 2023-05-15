package br.com.cooperativismo.service;

import br.com.cooperativismo.domain.VotingSession;
import br.com.cooperativismo.exception.InvalidVoteSessionException;
import br.com.cooperativismo.repository.VotingSessionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public record VotingSessionService(VotingSessionRepository votingSessionRepository) {

    public VotingSession getVotingSessionOnDatabase(UUID votingSessionId) {
        return votingSessionRepository.findById(votingSessionId)
                .switchIfEmpty(Mono.error(() -> new InvalidVoteSessionException(String.format("Session %s invalid or not registered", votingSessionId))))
                .mapNotNull(v -> v)
                .blockOptional()
                .orElseThrow();
    }
}