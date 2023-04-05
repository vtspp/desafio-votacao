package br.com.cooperativismo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class MeetingAgendaErrorResponse implements Response {
    private String messageError;
}