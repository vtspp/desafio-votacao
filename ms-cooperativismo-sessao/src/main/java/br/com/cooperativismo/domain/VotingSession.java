package br.com.cooperativismo.domain;

import br.com.cooperativismo.enumerate.SessionStatus;
import br.com.cooperativismo.enumerate.Vote;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Document
public record VotingSession(
        @MongoId
        UUID id,
        UUID meetingAgendaId,
        int minutesOfDuration,
        LocalTime sessionBegin,
        LocalTime sessionEnd,
        SessionStatus sessionStatus,
        Set<String> attendanceList,
        Map<Vote, Long> votes
) {
}