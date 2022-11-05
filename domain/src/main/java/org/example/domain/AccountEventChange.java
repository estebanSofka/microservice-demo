package org.example.domain;

import org.example.domain.events.AccountCreated;
import org.example.domain.events.AccountDeactivated;
import org.example.domain.events.TransactionAdded;
import org.example.domain.value.Balance;
import org.example.generic.domain.EventChange;

import java.util.HashMap;

public class AccountEventChange extends EventChange {
    public AccountEventChange(Account account) {
        apply((AccountCreated event) -> {
            account.name = event.getName();
            account.userId = event.getUserId();
            account.transactions = new HashMap<>();
            account.balance = new Balance(0.0);
            account.active = Boolean.TRUE;
        });

        apply((AccountDeactivated event) -> {
            account.active = Boolean.FALSE;
        });

        apply((TransactionAdded event) -> {
            if (account.active.equals(Boolean.FALSE))
                throw new IllegalArgumentException("The account is inactive.");
            account.transactions.put(event.getId(),
                    new Transaction(event.getId(), event.getTransactionDate(), event.getTransactionType(), event.getAmount())
            );
            switch (event.getTransactionType().value()) {
                case "DEPOSIT":
                case "ROI":
                    account.balance = new Balance(account.balance().value() + event.getAmount().value());
                    break;
                case "WITHDRAWAL":
                    account.balance = new Balance(account.balance().value() - event.getAmount().value());
                    break;
                default:
                    break;
            }
        });
    }
}
