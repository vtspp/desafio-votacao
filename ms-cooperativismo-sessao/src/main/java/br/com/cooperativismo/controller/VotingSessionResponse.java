package br.com.cooperativismo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.UUID;

@Getter
public final class VotingSessionResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID votingSessionId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String messageError;

    public VotingSessionResponse(UUID votingSessionId) {
        this.votingSessionId = votingSessionId;
    }

    public VotingSessionResponse(String messageError) {
        this.messageError = messageError;
    }
}