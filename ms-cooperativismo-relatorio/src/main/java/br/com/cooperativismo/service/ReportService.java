package br.com.cooperativismo.service;

import br.com.cooperativismo.controller.ReportVotingSessionResponse;
import br.com.cooperativismo.enumerate.Vote;
import br.com.cooperativismo.repository.VotingSessionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record ReportService(VotingSessionRepository votingSessionRepository) {

    public ReportVotingSessionResponse findVotingSessionResultById(UUID votingSessionId) {
        return votingSessionRepository.findById(votingSessionId)
                .map(v -> {
                    final var yes = v.votes().stream().filter(vote -> vote.equals(Vote.YES)).count();
                    final var no = v.votes().stream().filter(vote -> vote.equals(Vote.NO)).count();
                    return new ReportVotingSessionResponse(v.meetingAgendaId(), v.sessionStatus(), yes, no);
                })
                .block();
    }
}