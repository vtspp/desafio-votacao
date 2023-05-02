package br.com.cooperativismo.event;

import br.com.cooperativismo.enumerate.Vote;

import java.util.UUID;

public record VoteEvent(UUID id, UUID votingSessionId, Vote vote) {
}