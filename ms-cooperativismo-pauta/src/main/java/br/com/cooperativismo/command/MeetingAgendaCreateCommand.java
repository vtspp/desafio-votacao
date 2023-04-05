package br.com.cooperativismo.command;

import jakarta.validation.constraints.NotBlank;

public record MeetingAgendaCreateCommand(@NotBlank(message = "Field 'Meeting Agenda' is mandatory") String name) {
}