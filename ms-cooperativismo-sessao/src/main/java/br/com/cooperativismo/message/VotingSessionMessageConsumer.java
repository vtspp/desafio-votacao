package br.com.cooperativismo.message;

import br.com.cooperativismo.enumerate.SessionStatus;
import br.com.cooperativismo.event.VotingSessionEvent;
import br.com.cooperativismo.helper.DateTimeHelper;
import br.com.cooperativismo.service.ProcessEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public final class VotingSessionMessageConsumer {

    private final ProcessEventService service;
    @KafkaListener(id = "${spring.kafka.consumer.client-id}", topics = "${spring.kafka.template.default-topic}")
    public void handler(@Payload final ConsumerRecord<String, VotingSessionEvent> messageRecord, Acknowledgment acknowledgment) {
        final var consumerEvent = messageRecord.value();

        if (consumerEvent.sessionStatus() == SessionStatus.PENDING) {
            Mono.just(consumerEvent)
                    .doOnNext(e -> log.info("time={} method=#handler id={} eventStatus={} meetingAgenda={} handlerInfo=RECEIVED",
                            DateTimeHelper.LOCAL_DATE_TIME_FORMATTED, consumerEvent.id(), consumerEvent.sessionStatus(), consumerEvent.meetingVotingId()))
                    .transform(e -> service.process(e)
                            .doOnError(throwable -> log.error(
                                    "time={} method=#handler id={} eventStatus={} meetingAgendaId={} errorMessage={} handleInfo=ERROR",
                                    DateTimeHelper.LOCAL_DATE_TIME_FORMATTED, consumerEvent.id(), consumerEvent.sessionStatus(), consumerEvent.meetingVotingId(), throwable.getMessage()))
                            .onErrorComplete())
                    .doOnSuccess(x -> {
                        if (Objects.nonNull(x)) {
                            log.info("time={} method=#handler id={} eventStatus={} meetingAgendaId={} handlerInfo=SUCCESS",
                                    DateTimeHelper.LOCAL_DATE_TIME_FORMATTED, x.getId(), x.getSessionStatus(), x.getMeetingAgendaId());
                            acknowledgment.acknowledge();
                        }
                    })
                    .subscribe();
            return;
        }
        acknowledgment.acknowledge();
    }
}