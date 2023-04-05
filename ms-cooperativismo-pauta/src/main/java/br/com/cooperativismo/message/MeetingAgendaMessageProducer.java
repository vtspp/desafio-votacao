package br.com.cooperativismo.message;

import br.com.cooperativismo.event.MeetingAgendaCreateEvent;
import br.com.cooperativismo.helper.DateTimeHelper;
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
            log.info("Meeting-Agenda-Message-Producer status=SUCCESS time={} method=#send event={}", DateTimeHelper.LOCAL_DATE_TIME_FORMATTED, event);
            // envia métrica de sucesso
        } catch (RuntimeException e) {
            log.error("Meeting-Agenda-Message-Producer status=ERROR time={} method=#send event={} errorMessage={}", DateTimeHelper.LOCAL_DATE_TIME_FORMATTED, event, e.getMessage());
            // enviar métrica de erro
            throw e;
        }
    }
}