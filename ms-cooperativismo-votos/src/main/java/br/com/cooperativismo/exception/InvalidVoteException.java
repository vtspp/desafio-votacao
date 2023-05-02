package br.com.cooperativismo.exception;

public class InvalidVoteException extends  RuntimeException {
    public InvalidVoteException(String message) {
        super(message);
    }
}