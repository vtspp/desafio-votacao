package br.com.cooperativismo.event;

import br.com.cooperativismo.enumerate.VotingSessionStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record VotingSessionEvent(UUID id, int minutesOfDuration, VotingSessionStatus sessionStatus) {
}