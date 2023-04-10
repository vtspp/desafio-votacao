package br.com.cooperativismo.mapper;

import br.com.cooperativismo.domain.Vote;
import br.com.cooperativismo.event.VoteEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VoteMapper {

    public Vote mapperToEntity(VoteEvent event) {
        var vote = new Vote();
        vote.setId(event.id());
        vote.setMeetingAgendaId(event.meetingAgenda());
        vote.setMemberDocument(event.memberDocument());
        vote.setMemberVote(event.memberVote());
            return vote;
    }

    public VoteEvent mapperToEvent(Vote vote) {
        return new VoteEvent(vote.getId(), vote.getMeetingAgendaId(), vote.getMemberDocument(), vote.getMemberVote());
    }
}