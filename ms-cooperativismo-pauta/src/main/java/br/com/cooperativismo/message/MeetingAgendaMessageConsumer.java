package br.com.cooperativismo.message;

import br.com.cooperativismo.event.MeetingAgendaCreateEvent;
import br.com.cooperativismo.mapper.MeetingAgendaMapper;
import br.com.cooperativismo.repository.MeetingAgendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public final class MeetingAgendaMessageConsumer {

    private final MeetingAgendaRepository repository;
    @KafkaListener(id = "${spring.kafka.consumer.client-id}", topics = "ms.cooperativismo.pauta-topic")
    public void handler(ConsumerRecord<String, MeetingAgendaCreateEvent> messageRecord, Acknowledgment acknowledgment) {
        final MeetingAgendaCreateEvent event = messageRecord.value();
        Mono.just(event)
                .map(MeetingAgendaMapper::mapperToEntity)
                .subscribe(meetingAgenda -> repository.save(meetingAgenda)
                        .doOnSuccess(s ->  {
                            log.info("Meeting Agenda Message Consumer status=SUCCESS saved=true, id={}", s.getId());
                            // metric
                            acknowledgment.acknowledge();
                        })
                        .onErrorResume(throwable -> {
                            log.error("Meeting Agenda Message Consumer status=ERROR, saved=false, id={}, errorMessage={}, method=handler", event.getId(), throwable.getMessage());
                            // metric
                            return Mono.empty();
                        }).subscribe());
    }
}