package br.com.cooperativismo.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Data
@Document
public class MeetingAgenda {
    @MongoId
    private UUID id;
    @Indexed(unique = true)
    private String name;
}