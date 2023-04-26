package br.com.cooperativismo.dto;

import br.com.cooperativismo.enumerate.SessionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

public record VotingSessionResponse(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        UUID votingSessionId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        SessionStatus sessionStatus,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String messageError) {
}