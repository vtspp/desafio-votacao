package br.com.cooperativismo.mapper;

import br.com.cooperativismo.domain.MeetingAgenda;
import br.com.cooperativismo.event.MeetingAgendaCreateEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MeetingAgendaMapper {

    public MeetingAgenda mapperToEntity(MeetingAgendaCreateEvent event) {
            var meetingAgenda = new MeetingAgenda();
            meetingAgenda.setId(event.id());
            meetingAgenda.setName(event.name());
            return meetingAgenda;
    }
}