package br.com.cooperativismo.service.action;

import br.com.cooperativismo.domain.VotingSession;
import br.com.cooperativismo.enumerate.SessionStatus;

import java.time.LocalTime;

public final class VotingSessionCloseAction {

    public static VotingSession updateStatusToClosed(VotingSession votingSession) {
        if (shouldClose(votingSession.getSessionEnd())) {
            votingSession.setSessionStatus(SessionStatus.CLOSED);
        }
        return votingSession;
    }

    private static boolean shouldClose(LocalTime sessionEnd) {
        return LocalTime.now().isAfter(sessionEnd);
    }
}