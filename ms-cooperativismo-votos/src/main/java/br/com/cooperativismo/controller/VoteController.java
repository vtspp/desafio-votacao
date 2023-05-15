package br.com.cooperativismo.controller;

import br.com.cooperativismo.service.VoteService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/votes")
public record VoteController(VoteService service) {

    @PostMapping
    public Mono<ResponseEntity<VoteResponse>> openVotingSession(@Valid @RequestBody VoteRequest request) {
        return service.voteReceive(request)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .onErrorResume(WebExchangeBindException.class,
                        e -> Mono.just(ResponseEntity.status(e.getStatusCode())
                                .body(
                                        new VoteResponse(null, e.getAllErrors()
                                                .stream()
                                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                                .collect(Collectors.toList())
                                        )
                                )
                        )
                );
    }
}