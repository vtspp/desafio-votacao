package br.com.cooperativismo.controller;

import br.com.cooperativismo.command.VoteCommand;
import br.com.cooperativismo.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/votes")
@RequiredArgsConstructor
public final class VoteController {

    private final VoteService service;

    @PostMapping
    public Mono<ResponseEntity<VoteResponse>> openVotingSession(@Valid @RequestBody Mono<VoteCommand> command) {
        return service.receiveCommand(command)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .onErrorResume(WebExchangeBindException.class,
                        e -> Mono.just(ResponseEntity.status(e.getStatusCode())
                                .body(new VoteResponse(e.getFieldError().getDefaultMessage()))));
    }
}