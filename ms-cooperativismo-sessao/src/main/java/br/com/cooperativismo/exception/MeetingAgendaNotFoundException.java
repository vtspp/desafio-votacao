package br.com.cooperativismo.exception;

public class MeetingAgendaNotFoundException extends RuntimeException {

    public MeetingAgendaNotFoundException(String message) {
        super(message);
    }
}