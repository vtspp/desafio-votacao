package br.com.cooperativismo.service;

import br.com.cooperativismo.domain.VotingSession;
import br.com.cooperativismo.event.VotingSessionEvent;
import br.com.cooperativismo.mapper.VotingSessionMapper;
import br.com.cooperativismo.message.VotingSessionMessageProducer;
import br.com.cooperativismo.repository.VotingSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public final class EventService {

    private final VotingSessionMessageProducer messageProducer;
    private final VotingSessionRepository repository;

    public VotingSession processEvent(VotingSessionEvent consumerEvent) {
        return repository.findById(consumerEvent.id())
                .switchIfEmpty(Mono.just(VotingSessionMapper.mapperToEntity(consumerEvent)))
                .map(v -> v.getSessionStatus().applyBusinessRules(v))
                .doOnNext(v -> repository.save(v).subscribe())
                .map(o -> {
                    final var isUpdatedByStatus = o.getSessionStatus() != consumerEvent.sessionStatus();

                    if (isUpdatedByStatus) {
                        publishNewEvent(o);
                    }
                    return o;
                }).block();
    }

    private void publishNewEvent(VotingSession votingSession) {
        messageProducer.send(VotingSessionMapper.mapperToEvent(votingSession));
    }
}