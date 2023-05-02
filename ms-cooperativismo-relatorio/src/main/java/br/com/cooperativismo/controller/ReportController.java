package br.com.cooperativismo.controller;

import br.com.cooperativismo.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/reports")
public record ReportController(ReportService reportService) {

    @GetMapping("votingSession/{votingSession}/result")
    public Mono<ResponseEntity<?>> getVotingSessionResult(@PathVariable UUID votingSessionId) {
        return Mono.just(reportService.findVotingSessionResultById(votingSessionId))
                .map(r -> ResponseEntity.status(HttpStatus.OK).body(r));
    }
}