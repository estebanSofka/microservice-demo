package org.example.generic.business;

import org.example.generic.domain.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface EventStoreRepository {

    Flux<String> getAggretateIdsByCreationDate(String aggregateName, LocalDateTime creationDate);

    Flux<DomainEvent> getEventsBy(String aggregateName, String aggregateRootId);

    Mono<Void> saveEvent(String aggregateName, String aggregateRootId, StoredEvent storedEvent);

}