package br.com.cooperativismo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class MeetingAgendaCreateResponse implements Response {
    private UUID id;
    private String name;
}