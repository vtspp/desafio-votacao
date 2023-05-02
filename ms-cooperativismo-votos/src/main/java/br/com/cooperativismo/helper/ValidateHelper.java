package br.com.cooperativismo.helper;

import br.com.cooperativismo.domain.VotingSession;
import br.com.cooperativismo.enumerate.Vote;
import br.com.cooperativismo.exception.BusinessException;
import br.com.cooperativismo.exception.InvalidVoteException;
import br.com.cooperativismo.exception.VoteSessionClosedException;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class ValidateHelper {

    public void validateAssociateToVote(Set<String> attendanceList, String cpf) {
        if (attendanceList.contains(cpf))
            throw new BusinessException("Only 1 vote is allowed per CPF");
    }

    public void validateVoteOrThrowException(int voteIdentifier) {
        if (Vote.isIdentifierInvalid(voteIdentifier))
            throw new InvalidVoteException("Vote invalid");
    }

    public void validateSessionIsOpenOrThrowException(VotingSession votingSession) {
        if (votingSession.sessionStatus().isClosed())
            throw new VoteSessionClosedException(String.format("Session %s is closed. Vote not computed", votingSession.id()));
    }

}