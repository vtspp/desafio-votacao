package br.com.cooperativismo.enumerate;

public enum SessionStatus {
    OPEN(0),
    CLOSED (1);

    private int identifier;
    SessionStatus(int identifier) {
        this.identifier = identifier;
    }
}