package br.com.cooperativismo.service;

import br.com.cooperativismo.controller.MeetingAgendaCreateRequest;
import br.com.cooperativismo.controller.MeetingAgendaCreateResponse;
import br.com.cooperativismo.domain.MeetingAgenda;
import br.com.cooperativismo.helper.DateTimeHelper;
import br.com.cooperativismo.repository.MeetingAgendaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public record MeetingAgendaService(MeetingAgendaRepository meetingAgendaRepository) {

    public Mono<MeetingAgendaCreateResponse> createMeetingAgenda(Mono<MeetingAgendaCreateRequest> command) {
        return command.doOnNext(c -> log.info("time={} method=#createMeetingAgenda", DateTimeHelper.LOCAL_DATE_TIME_FORMATTED))
                .flatMap(r -> meetingAgendaRepository.save(new MeetingAgenda(UUID.randomUUID(), r.name())))
                .map(m -> new MeetingAgendaCreateResponse(m.id(), m.name(), null));
    }
}