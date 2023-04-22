package br.com.cooperativismo.message;

import br.com.cooperativismo.event.MeetingAgendaCreateEvent;
import br.com.cooperativismo.helper.DateTimeHelper;
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

    @KafkaListener(id = "${spring.kafka.consumer.client-id}", topics = "${spring.kafka.template.default-topic}")
    public void handler(final ConsumerRecord<String, MeetingAgendaCreateEvent> messageRecord, Acknowledgment acknowledgment) {
        var event = messageRecord.value();
        Mono.just(event)
                .doOnNext(e -> log.info("Meeting-Agenda-Message-Consumer time={} method=#handler id={}",
                        DateTimeHelper.LOCAL_DATE_TIME_FORMATTED, e.id()))
                .map(MeetingAgendaMapper::mapperToEntity)
                .subscribe(meetingAgenda -> repository.save(meetingAgenda)
                        .doOnSuccess(s ->  {
                            log.info("Meeting-Agenda-Message-Consumer status=SUCCESS time={} saved=true id={}",
                                    DateTimeHelper.LOCAL_DATE_TIME_FORMATTED, s.getId());
                            acknowledgment.acknowledge();
                        })
                        .onErrorResume(throwable -> {
                            log.error("Meeting-Agenda-Message-Consumer status=ERROR time={} saved=false  id={} errorMessage={} method=#handler",
                                    DateTimeHelper.LOCAL_DATE_TIME_FORMATTED, event.id(), throwable.getMessage());
                            return Mono.empty();
                        }).subscribe());
    }
}