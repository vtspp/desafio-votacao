package br.com.cooperativismo.controller;

import jakarta.validation.constraints.NotBlank;

public record MeetingAgendaCreateRequest(@NotBlank(message = "Field 'Meeting Agenda' is mandatory") String name) {
}