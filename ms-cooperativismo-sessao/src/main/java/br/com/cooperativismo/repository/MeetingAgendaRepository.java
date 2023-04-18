package br.com.cooperativismo.repository;

import br.com.cooperativismo.domain.MeetingAgenda;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MeetingAgendaRepository extends ReactiveMongoRepository<MeetingAgenda, UUID> {
}