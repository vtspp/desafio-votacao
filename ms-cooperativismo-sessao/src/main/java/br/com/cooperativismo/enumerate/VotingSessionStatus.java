package br.com.cooperativismo.enumerate;

import br.com.cooperativismo.domain.VotingSession;
import br.com.cooperativismo.strategy.StrategyByBusinessRules;

import java.time.LocalTime;

public enum VotingSessionStatus implements StrategyByBusinessRules<VotingSession> {

    PENDING {
        @Override
        public VotingSession applyBusinessRules(VotingSession votingSession) {
            final var durationDefault = 1;
            final var duration = Math.max(durationDefault, votingSession.getMinutesOfDuration());
            final var sessionBegin = LocalTime.now();
            final var sessionEnd = sessionBegin.plusMinutes(duration);

            votingSession.setSessionBegin(sessionBegin);
            votingSession.setSessionEnd(sessionEnd);
            votingSession.setMinutesOfDuration(duration);
            votingSession.setSessionStatus(OPEN);
            return votingSession;
        }
    },

    OPEN {
        @Override
        public VotingSession applyBusinessRules(VotingSession votingSession) {

            if (LocalTime.now().isAfter(votingSession.getSessionEnd())) {
                votingSession.setSessionStatus(CLOSED);
            }

            return votingSession;
        }
    },

    CLOSED {
        @Override
        public VotingSession applyBusinessRules(VotingSession votingSession) {
            return votingSession;
        }
    };

    public boolean isOpen() {
        return this == OPEN;
    }

    public boolean isClosed() {
        return this == CLOSED;
    }

    public boolean hasNextStatus() {
        return this != CLOSED;
    }
}