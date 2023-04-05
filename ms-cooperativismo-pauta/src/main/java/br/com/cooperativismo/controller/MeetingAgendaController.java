package br.com.cooperativismo.controller;

import br.com.cooperativismo.command.MeetingAgendaCreateCommand;
import br.com.cooperativismo.service.MeetingAgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/meetingAgendas")
public final class MeetingAgendaController {

    private final MeetingAgendaService service;

    @PostMapping
    public Mono<ResponseEntity<MeetingAgendaCreateResponse>> createMeetingAgenda(@Valid @RequestBody Mono<MeetingAgendaCreateCommand> command) {
        return service.createMeetingAgenda(command)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .onErrorResume(WebExchangeBindException.class,
                        e -> Mono.just(ResponseEntity.status(e.getStatusCode())
                                        .body(new MeetingAgendaCreateResponse(null,e.getFieldError().getDefaultMessage()))));
    }
}