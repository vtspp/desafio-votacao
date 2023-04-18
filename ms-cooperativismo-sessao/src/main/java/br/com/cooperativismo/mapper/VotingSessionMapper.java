package br.com.cooperativismo.mapper;

import br.com.cooperativismo.domain.VotingSession;
import br.com.cooperativismo.event.VotingSessionEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VotingSessionMapper {

    public VotingSession mapperToEntity(VotingSessionEvent event) {
        var votingSession = new VotingSession();
        votingSession.setId(event.id());
        votingSession.setMeetingAgendaId(event.meetingVotingId());
        votingSession.setMinutesOfDuration(event.minutesOfDuration());
        votingSession.setSessionStatus(event.sessionStatus());
        votingSession.setAssociates(null);
            return votingSession;
    }

    public VotingSessionEvent mapperToEvent(VotingSession votingSession) {
        return new VotingSessionEvent(
                votingSession.getId(),
                votingSession.getMeetingAgendaId(),
                votingSession.getMinutesOfDuration(),
                votingSession.getSessionStatus()
        );
    }
}