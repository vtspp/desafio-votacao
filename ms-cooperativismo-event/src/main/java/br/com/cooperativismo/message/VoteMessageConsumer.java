package br.com.cooperativismo.message;

import br.com.cooperativismo.event.VoteEvent;
import br.com.cooperativismo.helper.DateTimeHelper;
import br.com.cooperativismo.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public record VoteMessageConsumer(EventService service) {

    @KafkaListener(id = "${spring.kafka.consumer.client-id}", topics = "${spring.kafka.template.default-topic}")
    public void handler(@Payload final ConsumerRecord<String, VoteEvent> messageRecord, Acknowledgment acknowledgment) {
        final var consumerEvent = messageRecord.value();
        log.info("time={} method=#handler id={} eventStatus=RECEIVED", DateTimeHelper.getLocalDateTimeFormatted(), consumerEvent.id());
        service.processEvent(consumerEvent);
        acknowledgment.acknowledge();
    }
}