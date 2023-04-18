package br.com.cooperativismo.exception;

public class EventNotProcessedException extends RuntimeException {
    public EventNotProcessedException(String message) {
        super(message);
    }
}