package br.com.cooperativismo.command;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VotingSessionCommand(@NotNull(message = "Field 'meetingAgendaId' is mandatory") UUID meetingAgendaId, int minutesOfDuration) {
}