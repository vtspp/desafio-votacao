package br.com.cooperativismo.service;

import br.com.cooperativismo.controller.VoteRequest;
import br.com.cooperativismo.controller.VoteResponse;
import br.com.cooperativismo.enumerate.Vote;
import br.com.cooperativismo.event.VoteEvent;
import br.com.cooperativismo.exception.InvalidVoteException;
import br.com.cooperativismo.exception.InvalidVoteSessionException;
import br.com.cooperativismo.exception.VoteSessionClosedException;
import br.com.cooperativismo.message.VoteMessageProducer;
import br.com.cooperativismo.repository.VotingSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public record VoteService(VotingSessionRepository votingSessionRepository, VoteMessageProducer messageProducer) {

    public Mono<VoteResponse> voteReceive(Mono<VoteRequest> requestMono) {
        return requestMono.doOnNext(voteRequest -> validateVote(voteRequest.voteIdentifier()))
                .doOnNext(voteRequest -> validateSessionIsOpenOrThrowException(voteRequest.votingSessionId()))
                .map(c -> new VoteEvent(UUID.randomUUID(), c.votingSessionId(), Vote.getVoteByIdentifier(c.voteIdentifier())))
                .doOnNext(messageProducer::send)
                .doOnError(Mono::error)
                .map(e -> new VoteResponse(e.id(), null))
                .doOnSubscribe(s -> s.request(1));
    }

    private void validateVote(int voteIdentifier) {
        if (Vote.isIdentifierInvalid(voteIdentifier))
            throw new InvalidVoteException("Vote invalid");
    }

    private void validateSessionIsOpenOrThrowException(UUID votingSessionId) {
        votingSessionRepository.findById(votingSessionId)
                .blockOptional()
                .ifPresentOrElse(v -> {
                    if (v.getSessionStatus().isClosed())
                        throw new VoteSessionClosedException(String.format("Session %s is closed. Vote not computed", votingSessionId));
                    }, () -> new InvalidVoteSessionException(String.format("Session %s invalid or not registered", votingSessionId)));
    }
}