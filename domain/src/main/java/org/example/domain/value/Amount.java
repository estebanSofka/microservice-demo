package org.example.domain.value;

import org.example.generic.domain.ValueObject;

import java.util.Objects;

public class Amount implements ValueObject<Double> {
    private final Double value;

    public Amount(Double value) {
        this.value = Objects.requireNonNull(value, "Amount is null");

        if (this.value < 0) {
            throw new IllegalArgumentException("Amount can't be less 0.");
        }
    }

    @Override
    public Double value() {
        return value;
    }
}
