package br.com.cooperativismo.helper;

import br.com.cooperativismo.domain.VotingSession;
import br.com.cooperativismo.dto.VotingSessionRequest;
import br.com.cooperativismo.enumerate.SessionStatus;
import br.com.cooperativismo.enumerate.Vote;
import lombok.experimental.UtilityClass;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class ConvertHelper {

    public VotingSession of(VotingSessionRequest request) {
        final var sessionBegin = LocalTime.now();
        final var sessionEnd = sessionBegin.plusMinutes(request.minutesOfDuration());

        return new VotingSession(
                UUID.randomUUID(),
                request.meetingAgendaId(),
                request.minutesOfDuration(),
                sessionBegin,
                sessionEnd,
                SessionStatus.OPEN,
                new HashSet<>(0),
                Map.of(Vote.YES, 0L, Vote.NO, 0L));
    }
}
