package br.com.cooperativismo.message;

import br.com.cooperativismo.event.MeetingAgendaCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public final class MeetingAgendaMessageProducer {

    private final KafkaTemplate<String, MeetingAgendaCreateEvent> kafkaTemplate;

    public void send(MeetingAgendaCreateEvent event) {
        try {
            this.kafkaTemplate.send(kafkaTemplate.getDefaultTopic(), event);
            log.info("Meeting Agenda Message Producer status=SUCCESS event={}", event);
            // envia métrica de sucesso
        } catch (RuntimeException e) {
            log.error("Meeting Agenda Message Producer status=ERROR event={}, errorMessage={}, method=send", event, e.getMessage());
            // enviar métrica de erro
            throw e;
        }
    }
}