package br.com.cooperativismo.command;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VotingSessionCommand(int minutesOfDuration, @NotNull(message = "Field 'meetingAgenda' is mandatory") UUID meetingAgendaId) {
}