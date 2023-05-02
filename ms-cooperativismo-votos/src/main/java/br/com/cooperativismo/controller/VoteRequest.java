package br.com.cooperativismo.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VoteRequest(
        @NotNull(message = "Field 'votingSessionId' is mandatory")
        UUID votingSessionId,

        @NotBlank(message = "Field 'memberDocument' is mandatory")
        String memberDocument,

        @Min(value = 0, message = "Minimum vote is 0")
        @Max(value = 1, message = "Maxime vote is 1")
        int voteIdentifier) {
}