package br.com.cooperativismo.domain;

import br.com.cooperativismo.enumerate.Vote;
import lombok.Data;

import java.util.UUID;

@Data
public class Associate {
    private UUID id;
    private String cpf;
    private Vote vote;
}