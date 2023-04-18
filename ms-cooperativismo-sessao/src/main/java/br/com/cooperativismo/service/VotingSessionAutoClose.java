package br.com.cooperativismo.service;

import br.com.cooperativismo.domain.VotingSession;
import br.com.cooperativismo.enumerate.SessionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class VotingSessionAutoClose implements AutoCloseable {

    private static final long MINUTES = 1000L;
    private static final String SESSION_STATUS = "sessionStatus";
    private static final String MINUTES_OF_DURATION = "minutesOfDuration";
    private final ReactiveMongoTemplate mongoTemplate;
    private final ProcessEntityService processEntityService;

    @Override
    @Scheduled(fixedDelay = MINUTES)
    public void close() {
        mongoTemplate.find(queryBuilder(), VotingSession.class)
                        .subscribe(processEntityService::process);
    }

    private Query queryBuilder() {
        return Query.query(
                        Criteria.where(SESSION_STATUS)
                                .is(SessionStatus.OPEN))
                .with(Sort.by(Sort.Direction.ASC, MINUTES_OF_DURATION));
    }
}