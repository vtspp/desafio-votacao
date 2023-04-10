package br.com.cooperativismo.repository;

import br.com.cooperativismo.domain.Vote;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteRepository extends ReactiveMongoRepository<Vote, UUID> {
}