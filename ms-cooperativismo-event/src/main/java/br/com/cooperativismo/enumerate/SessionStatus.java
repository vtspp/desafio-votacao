package br.com.cooperativismo.enumerate;

public enum SessionStatus {
    OPEN ,
    CLOSED;

    public boolean isClosed() {
        return this == CLOSED;
    }
}