package org.example.application.command.adapter.repo;


import org.example.generic.DocumentEventStored;
import org.example.generic.business.EventSerializer;
import org.example.generic.business.EventStoreRepository;
import org.example.generic.business.StoredEvent;
import org.example.generic.domain.DomainEvent;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;

@Component
public class MongoEventStoreRepository implements EventStoreRepository {

    private final ReactiveMongoTemplate template;
    private final EventSerializer eventSerializer;


    public MongoEventStoreRepository(ReactiveMongoTemplate template, EventSerializer eventSerializer) {
        this.template = template;
        this.eventSerializer = eventSerializer;
    }

    @Override
    public Flux<String> getAggretateIdsByCreationDate(String aggregateName, LocalDateTime creationDate) {
        var query = new Query(Criteria
                .where("storedEvent.typeName").is("org.example.domain.events.AccountCreated")
                .and("storedEvent.occurredOn").lt(creationDate.with(LocalTime.MIN)));
        return template.find(query, DocumentEventStored.class, aggregateName)
                .sort(Comparator.comparing(event -> event.getStoredEvent().getOccurredOn()))
                .map(storeEvent -> storeEvent.getStoredEvent().deserializeEvent(eventSerializer).aggregateRootId());
    }

    @Override
    public Flux<DomainEvent> getEventsBy(String aggregateName, String aggregateRootId) {
        var query = new Query(Criteria.where("aggregateRootId").is(aggregateRootId));
        return template.find(query, DocumentEventStored.class, aggregateName)
                .sort(Comparator.comparing(event -> event.getStoredEvent().getOccurredOn()))
                .map(storeEvent -> storeEvent.getStoredEvent().deserializeEvent(eventSerializer));
    }

    @Override
    public Mono<Void> saveEvent(String aggregateName, String aggregateRootId, StoredEvent storedEvent) {
        var eventStored = new DocumentEventStored();
        eventStored.setAggregateRootId(aggregateRootId);
        eventStored.setStoredEvent(storedEvent);
        return template.save(eventStored, aggregateName).then();
    }
}

