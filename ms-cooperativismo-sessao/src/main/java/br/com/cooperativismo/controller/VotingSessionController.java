package br.com.cooperativismo.controller;

import br.com.cooperativismo.dto.VotingSessionRequest;
import br.com.cooperativismo.dto.VotingSessionResponse;
import br.com.cooperativismo.helper.DateTimeHelper;
import br.com.cooperativismo.service.VotingSessionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("api/v1/votingSessions")
public record VotingSessionController(
        VotingSessionService votingSessionService) {

    @PostMapping
    public Mono<ResponseEntity<VotingSessionResponse>> openVotingSession(@Valid @RequestBody Mono<VotingSessionRequest> requestMono) {
        return votingSessionService.createSession(requestMono)
                .map(response -> {
                    log.info("time={} method=#openVotingSession status=SUCCESS votingSessionId={}", DateTimeHelper.getLocalDateTimeFormatted(), response.votingSessionId());
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                })
                .onErrorResume(WebExchangeBindException.class,
                        e -> {
                            log.error("time={} method=#openVotingSession status=ERROR messageError={}", DateTimeHelper.getLocalDateTimeFormatted(), e.getFieldError().getDefaultMessage());
                            return Mono.just(ResponseEntity.status(e.getStatusCode())
                                    .body(new VotingSessionResponse(null, null, e.getFieldError().getDefaultMessage())));
                        });
    }
}