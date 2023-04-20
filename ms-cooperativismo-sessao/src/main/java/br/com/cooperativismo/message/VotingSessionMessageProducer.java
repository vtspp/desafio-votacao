package br.com.cooperativismo.message;

import br.com.cooperativismo.event.VotingSessionEvent;
import br.com.cooperativismo.helper.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public final class VotingSessionMessageProducer {

    private final KafkaTemplate<String, VotingSessionEvent> kafkaTemplate;

    public void send(VotingSessionEvent event) {
        try {
            this.kafkaTemplate.send(kafkaTemplate.getDefaultTopic(), event);
            log.info("Voting-Session-Message-Producer time={} status=SUCCESS method=#send event={}", DateTimeHelper.getLocalDateTimeFormatted(), event);
            // envia métrica de sucesso
        } catch (RuntimeException e) {
            log.error("Voting-Session-Message-Producer time={} status=ERROR method=#send event={} errorMessage={}", DateTimeHelper.getLocalDateTimeFormatted(), event, e.getMessage());
            // enviar métrica de erro
            throw e;
        }
    }
}