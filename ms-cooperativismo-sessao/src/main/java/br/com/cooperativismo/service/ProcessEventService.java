package br.com.cooperativismo.service;

import br.com.cooperativismo.domain.VotingSession;
import br.com.cooperativismo.event.VotingSessionEvent;
import br.com.cooperativismo.exception.MeetingAgendaNotRegisteredException;
import br.com.cooperativismo.helper.DateTimeHelper;
import br.com.cooperativismo.mapper.VotingSessionMapper;
import br.com.cooperativismo.message.VotingSessionMessageProducer;
import br.com.cooperativismo.repository.MeetingAgendaRepository;
import br.com.cooperativismo.repository.VotingSessionRepository;
import br.com.cooperativismo.strategy.ProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.BiConsumer;

@Slf4j
@Service
@RequiredArgsConstructor
public final class ProcessEventService implements ProcessService<Mono<VotingSessionEvent>, VotingSession> {

    private final VotingSessionMessageProducer messageProducer;
    private final VotingSessionRepository votingSessionRepository;
    private final MeetingAgendaRepository meetingAgendaRepository;

    @Override
    public Mono<VotingSession> process(Mono<VotingSessionEvent> consumerEvent) {
        return consumerEvent.doOnNext(e -> verifyMeetingAgendaIsRegisteredOrThrowException(e.meetingVotingId()))
                .mapNotNull(e -> votingSessionRepository.findById(e.id())
                        .switchIfEmpty(Mono.just(VotingSessionMapper.mapperToEntity(e)))
                        .map(v -> v.getSessionStatus().applyBusinessRules(v))
                        .doOnNext(v -> votingSessionRepository.save(v)
                                .subscribe())
                        .doOnNext(v -> log.info("time={} method=#process id={} votingSessionStatus={} meetingAgendaId={} processInfo=saved",
                                DateTimeHelper.LOCAL_DATE_TIME_FORMATTED, v.getId(), v.getSessionStatus(), v.getMeetingAgendaId()))
                        .doOnNext(v -> publishEventWhenStatusIsUpdated().accept(v, e)).block());
    }

    private BiConsumer<VotingSession, VotingSessionEvent> publishEventWhenStatusIsUpdated() {
        return (votingSession, event) -> {
            if (isStatusUpdated(votingSession, event))
                publishNewEvent(votingSession);
        };
    }

    private boolean isStatusUpdated(VotingSession votingSession , VotingSessionEvent consumerEvent) {
        return votingSession.getSessionStatus() != consumerEvent.sessionStatus();
    }

    private void publishNewEvent(VotingSession votingSession) {
        messageProducer.send(VotingSessionMapper.mapperToEvent(votingSession));
    }

    private void verifyMeetingAgendaIsRegisteredOrThrowException(final UUID meetingAgendaId) {
        meetingAgendaRepository.findById(meetingAgendaId)
                .blockOptional()
                .orElseThrow(() -> new MeetingAgendaNotRegisteredException("Meeting Agenda not registered"));
    }
}