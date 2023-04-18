package br.com.cooperativismo.domain;

import br.com.cooperativismo.enumerate.Vote;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Data
@Document
public class Associate {
    @MongoId
    private UUID id;
    @Indexed(unique = true)
    private String cpf;
    private Vote vote;
}