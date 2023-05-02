package br.com.cooperativismo.exception;

public class InvalidVoteSessionException extends  RuntimeException {
    public InvalidVoteSessionException(String message) {
        super(message);
    }
}