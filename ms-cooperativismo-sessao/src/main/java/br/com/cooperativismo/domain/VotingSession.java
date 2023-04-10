package br.com.cooperativismo.domain;

import br.com.cooperativismo.enumerate.VotingSessionStatus;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Document
public class VotingSession {

    private UUID id;
    private int minutesOfDuration = 1;
    private LocalTime sessionBegin;
    private LocalTime sessionEnd;

    @Indexed
    private VotingSessionStatus sessionStatus;

}