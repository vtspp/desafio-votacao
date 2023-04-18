package br.com.cooperativismo.domain;

import br.com.cooperativismo.enumerate.SessionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document
public class VotingSession {

    @MongoId
    private UUID id;
    private UUID meetingAgendaId;
    private int minutesOfDuration = 1;
    private LocalTime sessionBegin;
    private LocalTime sessionEnd;
    private SessionStatus sessionStatus;
    private Set<Associate> associates;
}