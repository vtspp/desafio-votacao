package br.com.cooperativismo.controller;

import br.com.cooperativismo.enumerate.SessionStatus;

import java.util.UUID;

public record ReportVotingSessionResponse(UUID meetingAgenda, SessionStatus sessionStatus, long yes, long no) {
}