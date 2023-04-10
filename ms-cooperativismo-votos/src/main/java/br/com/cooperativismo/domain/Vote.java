package br.com.cooperativismo.domain;

import br.com.cooperativismo.enumerate.MemberVote;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class Vote {

    private UUID id;

    @Indexed
    private UUID meetingAgendaId;
    private String memberDocument;
    private MemberVote memberVote;

}