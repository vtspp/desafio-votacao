package br.com.cooperativismo.message;

import br.com.cooperativismo.event.VoteEvent;
import br.com.cooperativismo.helper.DateTimeHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public record VoteMessageProducer(KafkaTemplate<String, VoteEvent> kafkaTemplate){

    public void send(VoteEvent event) {
        try {
            this.kafkaTemplate.send(kafkaTemplate.getDefaultTopic(), event);
            log.info("Vote-Message-Producer time={} status=SUCCESS method=#send event={}", DateTimeHelper.getLocalDateTimeFormatted(), event.getClass().getName());
            // envia métrica de sucesso
        } catch (RuntimeException e) {
            log.error("Vote-Message-Producer time={} status=ERROR method=#send event={} errorMessage={}", DateTimeHelper.getLocalDateTimeFormatted(), event.getClass().getName(), e.getMessage());
            // enviar métrica de erro
            throw e;
        }
    }
}