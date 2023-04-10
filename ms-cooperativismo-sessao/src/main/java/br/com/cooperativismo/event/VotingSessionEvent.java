package br.com.cooperativismo.event;

import br.com.cooperativismo.enumerate.VotingSessionStatus;

import java.util.UUID;

public record VotingSessionEvent(UUID id, int minutesOfDuration, VotingSessionStatus sessionStatus) {
}