package org.example.domain.value;

import org.example.generic.domain.ValueObject;

import java.time.LocalDateTime;

public class TransactionDate implements ValueObject<LocalDateTime> {

    private final LocalDateTime value;

    public TransactionDate(LocalDateTime value) {
        this.value = value;

        if (this.value.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Transaction date can't be before now.");
        }
    }

    @Override
    public LocalDateTime value() {
        return value;
    }
}
