package br.com.cooperativismo.service;

import br.com.cooperativismo.controller.VoteRequest;
import br.com.cooperativismo.controller.VoteResponse;
import br.com.cooperativismo.enumerate.Vote;
import br.com.cooperativismo.event.VoteEvent;
import br.com.cooperativismo.exception.InvalidVoteSessionException;
import br.com.cooperativismo.helper.ValidateHelper;
import br.com.cooperativismo.message.VoteMessageProducer;
import br.com.cooperativismo.repository.VotingSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public record VoteService(
        VotingSessionRepository votingSessionRepository,
        AssociateRepository associateRepository,
        VoteMessageProducer messageProducer) {

    public Mono<VoteResponse> voteReceive(Mono<VoteRequest> requestMono) {
        return requestMono
                .doOnNext(this::validateBusinessRules)
                .map(c -> new VoteEvent(UUID.randomUUID(), c.votingSessionId(), Vote.getVoteByIdentifier(c.voteIdentifier())))
                .doOnNext(messageProducer::send)
                .doOnError(Mono::error)
                .map(e -> new VoteResponse(e.id(), null))
                .doOnSubscribe(s -> s.request(1));
    }

    private void validateBusinessRules(VoteRequest request) {
        final var votingSessionId = request.votingSessionId();
        votingSessionRepository.findById(votingSessionId)
                .blockOptional()
                .ifPresentOrElse(v -> {
                    ValidateHelper.validateSessionIsOpenOrThrowException(v);
                    ValidateHelper.validateAssociateToVote(v.attendanceList(), request.cpf());
                    ValidateHelper.validateVoteOrThrowException(request.voteIdentifier());
                }, () -> new InvalidVoteSessionException(String.format("Session %s invalid or not registered", votingSessionId)));
    }
}