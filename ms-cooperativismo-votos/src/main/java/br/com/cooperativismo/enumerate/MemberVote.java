package br.com.cooperativismo.enumerate;

import java.util.Arrays;

public enum MemberVote {
    YES (0),
    NO(1);

    private int identifier;

    MemberVote(int identifier) {
        this.identifier = identifier;
    }

    public static MemberVote getMemberVoteByIdentifier(int identifier) {
        return Arrays.stream(MemberVote.values())
                .filter(memberVote -> memberVote.identifier == identifier)
                .findFirst()
                .orElse(NO);
    }
}