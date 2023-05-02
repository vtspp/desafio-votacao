package br.com.cooperativismo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

public record VoteResponse (
        @JsonInclude(JsonInclude.Include.NON_NULL) UUID voteId,
        @JsonInclude(JsonInclude.Include.NON_NULL) List<String> messageError) {
}