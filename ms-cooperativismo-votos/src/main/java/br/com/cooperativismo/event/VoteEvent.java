package br.com.cooperativismo.event;

import br.com.cooperativismo.enumerate.MemberVote;

import java.util.UUID;

public record VoteEvent(UUID id, UUID meetingAgenda, String memberDocument, MemberVote memberVote) {
}