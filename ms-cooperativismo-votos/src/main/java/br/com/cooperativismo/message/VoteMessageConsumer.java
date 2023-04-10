package br.com.cooperativismo.message;

import br.com.cooperativismo.event.VoteEvent;
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
public final class VoteMessageConsumer {

    private final EventService service;
    @KafkaListener(id = "${spring.kafka.consumer.client-id}", topics = "${spring.kafka.template.default-topic}")
    public void handler(@Payload final ConsumerRecord<String, VoteEvent> messageRecord, Acknowledgment acknowledgment) {
        final var consumerEvent = messageRecord.value();
        log.info("Vote-Message-Consumer time={} method=#handler id={} eventStatus={}", DateTimeHelper.LOCAL_DATE_TIME_FORMATTED, consumerEvent.id());

        try {

        } catch (RuntimeException e) {
            log.error("Erro para processar o evento {}", consumerEvent);
            acknowledgment.nack(Duration.ZERO);
        }
    }
}