package br.com.cooperativismo.message;

import br.com.cooperativismo.event.VotingSessionEvent;
import br.com.cooperativismo.helper.DateTimeHelper;
import br.com.cooperativismo.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public final class VotingSessionMessageConsumer {

    private final EventService service;
    @KafkaListener(id = "${spring.kafka.consumer.client-id}", topics = "${spring.kafka.template.default-topic}")
    public void handler(@Payload final ConsumerRecord<String, VotingSessionEvent> messageRecord, Acknowledgment acknowledgment) {
        final var consumerEvent = messageRecord.value();
        final var eventStatus = consumerEvent.sessionStatus();
        log.info("Voting-Session-Message-Consumer time={} method=#handler id={} eventStatus={}", DateTimeHelper.LOCAL_DATE_TIME_FORMATTED, consumerEvent.id(), eventStatus);

        try {

            var processedStatus = this.service.processEvent(consumerEvent).getSessionStatus();

            if (eventStatus.isOpen()) {

                if (processedStatus.isClosed()) {
                    acknowledgment.acknowledge();
                    return;
                }

                acknowledgment.nack(Duration.ZERO);
                return;
            }
            acknowledgment.acknowledge();

        } catch (RuntimeException e) {
            log.error("Erro para processar o evento {}", consumerEvent);
            acknowledgment.nack(Duration.ZERO);
        }
    }
}