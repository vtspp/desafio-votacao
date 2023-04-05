package br.com.cooperativismo.controller;

import br.com.cooperativismo.command.MeetingAgendaCreateCommand;
import br.com.cooperativismo.service.MeetingAgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/meetingAgendas")
public final class MeetingAgendaController {

    private final MeetingAgendaService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<MeetingAgendaCreateResponse> createMeetingAgenda(@RequestBody MeetingAgendaCreateCommand command) {
        return service.createMeetingAgenda(command);
    }
}