package org.example.application.command.adapter.service;

import org.example.business.gateway.ValidOwnerOfAccountService;
import org.example.domain.value.Name;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

@Component
public class RestValidOwnerOfAccountService implements ValidOwnerOfAccountService {
    @Override
    public Mono<Boolean> valid(Name name) {
        return Mono.just(Optional.ofNullable(name)
                .map(name1 -> true)
                .orElse(false));
    }
}
