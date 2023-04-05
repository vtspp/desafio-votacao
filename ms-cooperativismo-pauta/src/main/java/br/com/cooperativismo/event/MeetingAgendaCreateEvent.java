package br.com.cooperativismo.event;

import lombok.Builder;
import java.util.UUID;

@Builder
public record MeetingAgendaCreateEvent(UUID id, String name) {
}