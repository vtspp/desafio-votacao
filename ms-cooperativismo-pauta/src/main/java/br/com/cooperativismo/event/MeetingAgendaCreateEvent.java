package br.com.cooperativismo.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingAgendaCreateEvent {
    private UUID id;
    private String name;
}