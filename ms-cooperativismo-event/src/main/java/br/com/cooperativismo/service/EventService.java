package br.com.cooperativismo.service;

import br.com.cooperativismo.event.VoteEvent;
import br.com.cooperativismo.helper.DateTimeHelper;
import br.com.cooperativismo.repository.VotingSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public record EventService(VotingSessionRepository votingSessionRepository) {

    public void processEvent(VoteEvent consumerEvent) {
        Mono.just(votingSessionRepository)
                .map(r -> r.findById(consumerEvent.votingSessionId()))
                .subscribe(m -> m.doOnNext(v -> {
                            final var countVotes = v.votes().get(consumerEvent.vote());
                            v.votes().replace(consumerEvent.vote(), countVotes + 1);
                            v.attendanceList().add(consumerEvent.cpf());
                        })
                        .subscribe(v -> votingSessionRepository.save(v)
                                .subscribe(it -> log.info("time={} method=#processEvent eventStatus=SAVED votingSessionId={}, meetingAgendaId={}, cpf={}",
                                        DateTimeHelper.getLocalDateTimeFormatted(), v.id(), v.meetingAgendaId(), consumerEvent.cpf()))));
    }
}