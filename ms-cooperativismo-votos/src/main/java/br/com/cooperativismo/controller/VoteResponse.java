package br.com.cooperativismo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.UUID;

@Getter
public final class VoteResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID voteId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String messageError;

    public VoteResponse(UUID voteId) {
        this.voteId = voteId;
    }

    public VoteResponse(String messageError) {
        this.messageError = messageError;
    }
}