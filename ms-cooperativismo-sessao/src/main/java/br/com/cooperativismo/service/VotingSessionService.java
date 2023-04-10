package br.com.cooperativismo.service;

import br.com.cooperativismo.command.VotingSessionCommand;
import br.com.cooperativismo.controller.VotingSessionResponse;
import br.com.cooperativismo.enumerate.VotingSessionStatus;
import br.com.cooperativismo.event.VotingSessionEvent;
import br.com.cooperativismo.helper.DateTimeHelper;
import br.com.cooperativismo.message.VotingSessionMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public final class VotingSessionService {

    private final VotingSessionMessageProducer messageProducer;

    public Mono<VotingSessionResponse> receiveCommand(Mono<VotingSessionCommand> command) {
        return command.doOnNext(c -> log.info("Voting-Session-Service time={} method=#receiveCommand", DateTimeHelper.LOCAL_DATE_TIME_FORMATTED))
                .map(c -> new VotingSessionEvent(UUID.randomUUID(), c.minutesOfDuration(), VotingSessionStatus.PENDING))
                .doOnNext(messageProducer::send)
                .map(event -> new VotingSessionResponse(event.id(), event.sessionStatus()));
    }
}