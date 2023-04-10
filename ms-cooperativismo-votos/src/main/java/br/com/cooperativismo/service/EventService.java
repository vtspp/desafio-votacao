package br.com.cooperativismo.service;

import br.com.cooperativismo.domain.Vote;
import br.com.cooperativismo.event.VoteEvent;
import br.com.cooperativismo.mapper.VoteMapper;
import br.com.cooperativismo.message.VoteMessageProducer;
import br.com.cooperativismo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public final class EventService {

    private final VoteMessageProducer messageProducer;
    private final VoteRepository repository;

    public Vote processEvent(VoteEvent consumerEvent) {
        return repository.findById(consumerEvent.id())
                .switchIfEmpty(Mono.just(VoteMapper.mapperToEntity(consumerEvent)))
                .block();
    }

    private void publishNewEvent(Vote vote) {
        messageProducer.send(VoteMapper.mapperToEvent(vote));
    }
}