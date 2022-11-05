package org.example.domain.events;

import org.example.domain.value.Amount;
import org.example.domain.value.TransactionDate;
import org.example.domain.value.TransactionId;
import org.example.domain.value.TransactionType;
import org.example.generic.domain.DomainEvent;

public class TransactionAdded extends DomainEvent {
    private final TransactionId id;
    private final TransactionDate transactionDate;
    private final TransactionType transactionType;
    private final Amount amount;

    public TransactionAdded(TransactionId id, TransactionDate transactionDate, TransactionType transactionType, Amount amount) {
        super("org.example.TransactionAdded");
        this.id = id;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public TransactionDate getTransactionDate() {
        return transactionDate;
    }

    public TransactionId getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Amount getAmount() {
        return amount;
    }
}
