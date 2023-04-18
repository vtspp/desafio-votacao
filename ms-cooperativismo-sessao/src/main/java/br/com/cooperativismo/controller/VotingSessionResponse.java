package br.com.cooperativismo.controller;

import br.com.cooperativismo.enumerate.SessionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.UUID;

@Getter
public final class VotingSessionResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID votingSessionId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SessionStatus sessionStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String messageError;

    public VotingSessionResponse(UUID votingSessionId, SessionStatus sessionStatus) {
        this.votingSessionId = votingSessionId;
        this.sessionStatus = sessionStatus;
    }

    public VotingSessionResponse(String messageError) {
        this.messageError = messageError;
    }
}