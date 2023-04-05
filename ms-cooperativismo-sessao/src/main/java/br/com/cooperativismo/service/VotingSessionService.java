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
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public final class VotingSessionService {

    private final VotingSessionMessageProducer messageProducer;

    public Mono<VotingSessionResponse> receiverCommand(Mono<VotingSessionCommand> command) {
        return command
                .doOnNext(c -> log.info("Voting Session Service receiveCommand={}, localDateTime={}", c, DateTimeHelper.LOCAL_DATE_TIME_FORMATTED))
                .map(this.generateEvent())
                .doOnNext(messageProducer::send)
                .map(event -> new VotingSessionResponse(event.id(), event.sessionStatus()));
    }

    private Function<VotingSessionCommand, VotingSessionEvent> generateEvent() {
        return command -> VotingSessionEvent.builder()
                .id(UUID.randomUUID())
                .minutesOfDuration(command.minutesOfDuration())
                .sessionStatus(VotingSessionStatus.PENDING)
                .build();
    }
}