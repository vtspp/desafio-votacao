package br.com.cooperativismo.service;

import br.com.cooperativismo.dto.VotingSessionRequest;
import br.com.cooperativismo.dto.VotingSessionResponse;
import br.com.cooperativismo.exception.MeetingAgendaNotFoundException;
import br.com.cooperativismo.helper.ConvertHelper;
import br.com.cooperativismo.repository.MeetingAgendaRepository;
import br.com.cooperativismo.repository.VotingSessionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public record VotingSessionService(MeetingAgendaRepository meetingAgendaRepository, VotingSessionRepository votingSessionRepository) {

    public Mono<VotingSessionResponse> createSession(Mono<VotingSessionRequest> request) {
        return request
                .doOnNext(r -> meetingAgendaRepository.findById(r.meetingAgendaId())
                        .switchIfEmpty(
                                Mono.error(() -> new MeetingAgendaNotFoundException(String.format("MeetingAgenda %s invalid or not registered", r.meetingAgendaId()))))
                        .subscribe())
                .flatMap(r -> votingSessionRepository.save(ConvertHelper.of(r)))
                .doOnError(Mono::error)
                .map(m -> new VotingSessionResponse(m.id(), m.sessionStatus(), null));
    }
}