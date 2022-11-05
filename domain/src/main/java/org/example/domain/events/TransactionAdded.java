package org.example.domain.events;

import org.example.domain.value.*;
import org.example.generic.domain.DomainEvent;

public class TransactionAdded extends DomainEvent {
    private final TransactionId id;
    private final TransactionDate transactionDate;
    private final TransactionType transactionType;
    private final Amount amount;
    private final Name name;

    public TransactionAdded(TransactionId id, TransactionDate transactionDate, TransactionType transactionType, Amount amount) {
        super("org.example.TransactionAdded");
        this.id = id;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
        this.name = new Name("Creation");
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

    public Name getName() {
        return name;
    }
}
