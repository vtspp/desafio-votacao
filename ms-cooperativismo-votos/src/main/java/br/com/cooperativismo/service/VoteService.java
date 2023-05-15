package br.com.cooperativismo.service;

import br.com.cooperativismo.controller.VoteRequest;
import br.com.cooperativismo.controller.VoteResponse;
import br.com.cooperativismo.enumerate.Vote;
import br.com.cooperativismo.event.VoteEvent;
import br.com.cooperativismo.helper.ValidateHelper;
import br.com.cooperativismo.message.VoteMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public record VoteService(
        VotingSessionService votingSessionService,
        VoteMessageProducer messageProducer) {

    public Mono<VoteResponse> voteReceive(VoteRequest request) {
        final var session = votingSessionService.getVotingSessionOnDatabase(request.votingSessionId());
        ValidateHelper.validateSessionIsOpenOrThrowException(session);
        ValidateHelper.validateAssociateToVote(session.attendanceList(), request.cpf());
        ValidateHelper.validateVoteOrThrowException(request.voteIdentifier());
        return Mono.just(request)
                .map(r -> new VoteEvent(UUID.randomUUID(), r.votingSessionId(), r.cpf(), Vote.getVoteByIdentifier(r.voteIdentifier())))
                .doOnNext(messageProducer::send)
                .flatMap(e -> Mono.just(new VoteResponse(e.id(), null)));
    }
}