package br.com.cooperativismo.service.action;

import br.com.cooperativismo.domain.VotingSession;
import br.com.cooperativismo.enumerate.SessionStatus;

import java.time.LocalTime;

public final class VotingSessionOpenAction {

    private static final int DURATION_DEFAULT = 1;
    public static VotingSession updateStatusToOpen(VotingSession votingSession) {
        final var duration = Math.max(DURATION_DEFAULT, votingSession.getMinutesOfDuration());
        final var sessionBegin = LocalTime.now();
        final var sessionEnd = sessionBegin.plusMinutes(duration);

        votingSession.setSessionBegin(sessionBegin);
        votingSession.setSessionEnd(sessionEnd);
        votingSession.setMinutesOfDuration(duration);
        votingSession.setSessionStatus(SessionStatus.OPEN);
        return votingSession;
    }
}