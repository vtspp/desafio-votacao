package br.com.cooperativismo.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class VotingSession {
    private final UUID id;
    private int minutesOfDuration = 1;
}