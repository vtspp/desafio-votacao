package br.com.cooperativismo.service;

import br.com.cooperativismo.command.VoteCommand;
import br.com.cooperativismo.controller.VoteResponse;
import br.com.cooperativismo.enumerate.MemberVote;
import br.com.cooperativismo.event.VoteEvent;
import br.com.cooperativismo.helper.DateTimeHelper;
import br.com.cooperativismo.message.VoteMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public final class VoteService {

    private final VoteMessageProducer messageProducer;

    public Mono<VoteResponse> receiveCommand(Mono<VoteCommand> command) {
        return command.doOnNext(c -> log.info("Vote-Service time={} method=#receiveCommand", DateTimeHelper.LOCAL_DATE_TIME_FORMATTED))
                .map(c -> new VoteEvent(UUID.randomUUID(), c.meetingAgendaId(), c.memberDocument(), MemberVote.getMemberVoteByIdentifier(c.memberVote())))
                .doOnNext(messageProducer::send)
                .map(event -> new VoteResponse(event.id()));
    }
}