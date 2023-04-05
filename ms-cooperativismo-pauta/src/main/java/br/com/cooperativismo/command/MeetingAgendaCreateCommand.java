package br.com.cooperativismo.command;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class MeetingAgendaCreateCommand {
    @NotBlank(message = "Field 'Meeting Agenda' is mandatory")
    private String name;
}