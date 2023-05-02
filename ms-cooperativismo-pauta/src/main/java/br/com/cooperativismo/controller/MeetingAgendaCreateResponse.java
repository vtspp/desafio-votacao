package br.com.cooperativismo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

public record MeetingAgendaCreateResponse(
        @JsonInclude(JsonInclude.Include.NON_NULL) UUID id,
        @JsonInclude(JsonInclude.Include.NON_NULL) String name,
        @JsonInclude(JsonInclude.Include.NON_NULL) String messageError
) {
}