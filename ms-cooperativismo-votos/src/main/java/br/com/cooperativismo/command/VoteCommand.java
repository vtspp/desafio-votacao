package br.com.cooperativismo.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VoteCommand(
        @NotNull(message = "Field 'meetingAgendaId' is mandatory")
        UUID meetingAgendaId,

        @NotBlank(message = "Field 'memberDocument' is mandatory")
        String memberDocument,

        @Min(value = 0, message = "Minimum values is 0")
        @Max(value = 1, message = "Maxime values is 1")
        int memberVote) {
}