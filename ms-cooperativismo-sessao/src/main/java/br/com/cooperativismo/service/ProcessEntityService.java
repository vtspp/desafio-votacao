package br.com.cooperativismo.service;

import br.com.cooperativismo.domain.VotingSession;
import br.com.cooperativismo.mapper.VotingSessionMapper;
import br.com.cooperativismo.message.VotingSessionMessageProducer;
import br.com.cooperativismo.repository.VotingSessionRepository;
import br.com.cooperativismo.strategy.ProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public final class ProcessEntityService implements ProcessService<VotingSession, Void> {

    private final VotingSessionMessageProducer messageProducer;
    private final VotingSessionRepository votingSessionRepository;

    @Override
    public Mono<Void> process(VotingSession votingSession) {
        Mono.just(votingSession)
                .map(v -> v.getSessionStatus().applyBusinessRules(v))
                .filter(this::isStatusUpdated)
                .doOnNext(v -> votingSessionRepository.save(v)
                        .subscribe(this::publishNewEvent))
                .subscribe();
        return Mono.empty();
    }

    private boolean isStatusUpdated(VotingSession votingSession) {
        return !votingSession.getSessionStatus().hasNextStatus();
    }

    private void publishNewEvent(VotingSession votingSession) {
        messageProducer.send(VotingSessionMapper.mapperToEvent(votingSession));
    }
}