package br.com.cooperativismo.exception;

public class MeetingAgendaNotRegisteredException extends RuntimeException {

    public MeetingAgendaNotRegisteredException(final String message) {
        super(message);
    }
}