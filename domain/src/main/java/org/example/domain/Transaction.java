package org.example.domain;

import org.example.domain.value.Amount;
import org.example.domain.value.TransactionDate;
import org.example.domain.value.TransactionId;
import org.example.domain.value.TransactionType;
import org.example.generic.domain.Entity;

public class Transaction extends Entity<TransactionId> {

    private TransactionDate transactionDate;
    private TransactionType transactionType;
    private Amount amount;

    public Transaction(TransactionId id, TransactionDate transactionDate, TransactionType transactionType, Amount amount) {
        super(id);
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
    }
}
