package br.com.cooperativismo.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VotingSessionRequest(@NotNull(message = "Field 'meetingAgendaId' is mandatory") UUID meetingAgendaId, int minutesOfDuration) {
}