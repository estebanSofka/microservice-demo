package org.example.domain.value;

import org.example.generic.domain.ValueObject;

import java.util.Objects;

public class ROIRate implements ValueObject<Double> {
    private final Double value;

    public ROIRate(Double value) {
        this.value = Objects.requireNonNull(value, "ROIRate is null");

        if (this.value < 0) {
            throw new IllegalArgumentException("ROIRate can't be less or equals to 0.");
        }
    }

    @Override
    public Double value() {
        return value;
    }
}
