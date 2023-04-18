package br.com.cooperativismo.enumerate;

import br.com.cooperativismo.domain.VotingSession;
import br.com.cooperativismo.strategy.StrategyByBusinessRules;
import br.com.cooperativismo.service.action.VotingSessionCloseAction;
import br.com.cooperativismo.service.action.VotingSessionOpenAction;

public enum SessionStatus implements StrategyByBusinessRules<VotingSession> {

    PENDING {
        @Override
        public VotingSession applyBusinessRules(VotingSession votingSession) {
            return VotingSessionOpenAction.updateStatusToOpen(votingSession);
        }
    },

    OPEN {
        @Override
        public VotingSession applyBusinessRules(VotingSession votingSession) {
            return VotingSessionCloseAction.updateStatusToClosed(votingSession);
        }
    },

    CLOSED {
        @Override
        public VotingSession applyBusinessRules(VotingSession votingSession) {
            return votingSession;
        }
    };

    public boolean hasNextStatus() {
        return this != CLOSED;
    }
}