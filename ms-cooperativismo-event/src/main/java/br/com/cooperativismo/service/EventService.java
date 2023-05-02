package br.com.cooperativismo.service;

import br.com.cooperativismo.event.VoteEvent;
import br.com.cooperativismo.repository.VotingSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public final class EventService {

    private final VotingSessionRepository votingSessionRepository;

    public void processEvent(VoteEvent consumerEvent) {
        Mono.just(votingSessionRepository)
                 .map(r -> r.findById(consumerEvent.votingSessionId()))
                 .subscribe(m -> m.doOnNext(v -> {
                             v.votes().add(consumerEvent.vote());
                             v.attendanceList().add(consumerEvent.cpf());
                         })
                         .subscribe(v -> votingSessionRepository.save(v)
                                 .subscribe(it -> log.info("Vote computed votingSessionId={}, meetingAgendaId={}", v.id(), v.meetingAgendaId()))));
    }
}