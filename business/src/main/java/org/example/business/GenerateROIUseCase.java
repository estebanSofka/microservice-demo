package org.example.business;

import org.example.domain.Account;
import org.example.domain.command.GenerateROIUseCommand;
import org.example.domain.value.*;
import org.example.generic.business.EventStoreRepository;
import org.example.generic.domain.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.function.Function;

public class GenerateROIUseCase implements Function<Mono<GenerateROIUseCommand>, Flux<DomainEvent>> {

    private final EventStoreRepository repository;

    public GenerateROIUseCase(EventStoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<GenerateROIUseCommand> commandMono) {
        return commandMono.flatMapMany(command -> {
            var roiRate = command.getRoiRate();
            return repository.getAggretateIdsByCreationDate("account", LocalDateTime.now()).flatMap(aggregateId ->
                    repository.getEventsBy("account", aggregateId).collectList().flatMapIterable(domainEvents -> {
                        var account = Account.from(AccountId.of(aggregateId), domainEvents);
                        var amount = new Amount(account.balance().value() * roiRate.value());
                        if (account.balance().value() > 0) {
                            account.addTransaction(new TransactionId(), new TransactionDate(LocalDateTime.now()), new TransactionType("ROI"), amount);
                        }
                        return account.getUncommittedChanges();
                    })
            );
        });
    }
}
