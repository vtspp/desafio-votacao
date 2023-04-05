package br.com.cooperativismo.service;

import br.com.cooperativismo.command.MeetingAgendaCreateCommand;
import br.com.cooperativismo.controller.MeetingAgendaCreateResponse;
import br.com.cooperativismo.event.MeetingAgendaCreateEvent;
import br.com.cooperativismo.message.MeetingAgendaMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public final class MeetingAgendaService {

    private final MeetingAgendaMessageProducer producer;

    public Mono<MeetingAgendaCreateResponse> createMeetingAgenda(Mono<MeetingAgendaCreateCommand> command) {
        return command
                .map(this.generateEvent())
                .doOnNext(producer::send)
                .map(event -> new MeetingAgendaCreateResponse(event.getId(), event.getName()));
    }

    private Function<MeetingAgendaCreateCommand, MeetingAgendaCreateEvent> generateEvent() {
        return command -> new MeetingAgendaCreateEvent(UUID.randomUUID(), command.getName());
    }
}