package org.example.domain.command;

import org.example.domain.value.AccountId;
import org.example.domain.value.Amount;
import org.example.domain.value.TransactionDate;
import org.example.domain.value.TransactionType;
import org.example.generic.domain.Command;

import java.time.LocalDateTime;

public class AddTransactionUseCommand extends Command {
    private final AccountId id;
    private final LocalDateTime transactionDate;
    private final TransactionType transactionType;
    private final Amount amount;

    public AddTransactionUseCommand(AccountId id, LocalDateTime transactionDate, TransactionType transactionType, Amount amount) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public AccountId getId() {
        return id;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Amount getAmount() {
        return amount;
    }
}
