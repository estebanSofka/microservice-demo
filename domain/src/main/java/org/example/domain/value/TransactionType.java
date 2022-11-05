package org.example.domain.value;

import org.example.generic.domain.ValueObject;

import java.util.Objects;

public class TransactionType implements ValueObject<String> {
    private final String value;

    public TransactionType(String value) {
        this.value = Objects.requireNonNull(value, "The transaction type is null");
        if (this.value.isEmpty()) {
            throw new IllegalArgumentException("The transaction type is empty");
        }
    }

    @Override
    public String value() {
        return value;
    }
}
