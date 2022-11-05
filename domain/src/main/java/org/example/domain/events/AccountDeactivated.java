package org.example.domain.events;

public class AccountDeactivated extends org.example.generic.domain.DomainEvent {

    public AccountDeactivated() {
        super("org.example.AccountDeactivated");
    }
}
