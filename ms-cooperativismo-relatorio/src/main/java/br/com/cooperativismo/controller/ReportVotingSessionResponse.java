package br.com.cooperativismo.controller;

import br.com.cooperativismo.enumerate.SessionStatus;
import br.com.cooperativismo.enumerate.Vote;

import java.util.Map;
import java.util.UUID;

public record ReportVotingSessionResponse(UUID meetingAgenda, SessionStatus sessionStatus, Map<Vote, Long> votes) {
}