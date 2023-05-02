package br.com.cooperativismo.exception;

public class VoteSessionClosedException extends  RuntimeException {
    public VoteSessionClosedException(String message) {
        super(message);
    }
}