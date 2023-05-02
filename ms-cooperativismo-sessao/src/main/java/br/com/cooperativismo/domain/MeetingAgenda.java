package br.com.cooperativismo.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Document
public record MeetingAgenda(
        @MongoId UUID id,
        @Indexed(unique = true) String name) {
}