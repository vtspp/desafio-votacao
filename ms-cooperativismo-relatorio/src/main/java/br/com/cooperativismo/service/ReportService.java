package br.com.cooperativismo.service;

import br.com.cooperativismo.controller.ReportVotingSessionResponse;
import br.com.cooperativismo.repository.VotingSessionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public record ReportService(VotingSessionRepository votingSessionRepository) {

    public Mono<ReportVotingSessionResponse> findVotingSessionResultById(UUID votingSessionId) {
        return votingSessionRepository.findById(votingSessionId)
                .map(v -> new ReportVotingSessionResponse(v.meetingAgendaId(), v.sessionStatus(), v.votes()));
    }
}