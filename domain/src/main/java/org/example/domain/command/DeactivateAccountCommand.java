package org.example.domain.command;

import org.example.domain.value.AccountId;
import org.example.generic.domain.Command;

public class DeactivateAccountCommand extends Command {

    private final AccountId id;

    public DeactivateAccountCommand(AccountId id) {
        this.id = id;
    }

    public AccountId getId() {
        return id;
    }
}
