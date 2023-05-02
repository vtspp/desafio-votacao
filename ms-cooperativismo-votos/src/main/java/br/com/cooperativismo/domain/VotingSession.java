package br.com.cooperativismo.domain;

import br.com.cooperativismo.enumerate.SessionStatus;
import br.com.cooperativismo.enumerate.Vote;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalTime;
import java.util.*;

@Data
@Document
public class VotingSession {

    @MongoId
    private UUID id;
    private UUID meetingAgendaId;
    private int minutesOfDuration = 1;
    private LocalTime sessionBegin;
    private LocalTime sessionEnd;
    private SessionStatus sessionStatus;
    private List<Vote> votes = new ArrayList<>(0);

}