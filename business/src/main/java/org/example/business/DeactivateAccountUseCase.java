package org.example.business;

import org.example.domain.Account;
import org.example.domain.command.DeactivateAccountCommand;
import org.example.generic.business.EventStoreRepository;
import org.example.generic.domain.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class DeactivateAccountUseCase implements Function<Mono<DeactivateAccountCommand>, Flux<DomainEvent>> {

    private final EventStoreRepository repository;

    public DeactivateAccountUseCase(EventStoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<DeactivateAccountCommand> deleteAccountCommandMono) {
        return deleteAccountCommandMono.flatMapMany(command ->
                repository.getEventsBy("account", command.getId().value())
                        .collectList()
                        .flatMapIterable(domainEvents -> {
                            var account = Account.from(command.getId(), domainEvents);

                            account.inactivateAccount();

                            return account.getUncommittedChanges();
                        })
        );
    }
}
