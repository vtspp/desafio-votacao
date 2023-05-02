package br.com.cooperativismo.enumerate;

public enum SessionStatus {
    CLOSED;

    public boolean isClosed() {
        return this == CLOSED;
    }
}