package br.com.cooperativismo.enumerate;

import java.util.Arrays;

public enum Vote {
    YES (0),
    NO(1);

    private int voteIdentifier;

    Vote(int voteIdentifier) {
        this.voteIdentifier = voteIdentifier;
    }

    public static Vote getVoteByIdentifier(int voteIdentifier) {
        return Vote.values()[voteIdentifier];
    }

    public static boolean isIdentifierInvalid(int voteIdentifier) {
        return Arrays.stream(values()).noneMatch(vote -> vote.voteIdentifier == voteIdentifier);
    }
}