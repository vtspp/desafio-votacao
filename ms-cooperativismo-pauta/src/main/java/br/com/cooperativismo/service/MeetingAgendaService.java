package br.com.cooperativismo.service;

import br.com.cooperativismo.command.MeetingAgendaCreateCommand;
import br.com.cooperativismo.controller.MeetingAgendaCreateResponse;
import br.com.cooperativismo.event.MeetingAgendaCreateEvent;
import br.com.cooperativismo.helper.DateTimeHelper;
import br.com.cooperativismo.message.MeetingAgendaMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public final class MeetingAgendaService {

    private final MeetingAgendaMessageProducer producer;

    public Mono<MeetingAgendaCreateResponse> receiverCommand(Mono<MeetingAgendaCreateCommand> command) {
        return command
                    .doOnNext(c -> log.info("Meeting-Agenda-Message-Service time={} method=#receiveCommand", DateTimeHelper.LOCAL_DATE_TIME_FORMATTED))
                .map(this.generateEvent())
                .doOnNext(producer::send)
                .map(event -> new MeetingAgendaCreateResponse(event.id(), event.name()));
    }

    private Function<MeetingAgendaCreateCommand, MeetingAgendaCreateEvent> generateEvent() {
        return command -> MeetingAgendaCreateEvent.builder()
                .id(UUID.randomUUID())
                .name(command.name())
                .build();
    }
}