package br.com.cooperativismo.mapper;

import br.com.cooperativismo.domain.MeetingAgenda;
import br.com.cooperativismo.event.MeetingAgendaEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MeetingAgendaMapper {

    public MeetingAgenda mapperToEntity(MeetingAgendaEvent event) {
            MeetingAgenda meetingAgenda = new MeetingAgenda();
            meetingAgenda.setId(event.getId());
            meetingAgenda.setName(event.getName());
            return meetingAgenda;
    }
}