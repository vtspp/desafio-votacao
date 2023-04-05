package br.com.cooperativismo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.UUID;

@Getter
public final class MeetingAgendaCreateResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String messageError;

    public MeetingAgendaCreateResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public MeetingAgendaCreateResponse(String messageError) {
        this.messageError = messageError;
    }
}