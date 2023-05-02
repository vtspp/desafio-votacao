package br.com.cooperativismo.controller;

import br.com.cooperativismo.service.MeetingAgendaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/meetingAgendas")
public record MeetingAgendaController(MeetingAgendaService meetingAgendaService) {

    @PostMapping
    public Mono<ResponseEntity<MeetingAgendaCreateResponse>> createMeetingAgenda(@Valid @RequestBody Mono<MeetingAgendaCreateRequest> requestMono) {
        return meetingAgendaService.createMeetingAgenda(requestMono)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .onErrorResume(WebExchangeBindException.class,
                        e -> Mono.just(ResponseEntity.status(e.getStatusCode())
                                        .body(new MeetingAgendaCreateResponse(null, null, e.getFieldError().getDefaultMessage()))));
    }
}