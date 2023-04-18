package br.com.cooperativismo.event;

import br.com.cooperativismo.enumerate.SessionStatus;

import java.util.UUID;

public record VotingSessionEvent(UUID id, UUID meetingVotingId, int minutesOfDuration, SessionStatus sessionStatus) {
}