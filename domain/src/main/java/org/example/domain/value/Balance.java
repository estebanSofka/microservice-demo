package org.example.domain.value;

import org.example.generic.domain.ValueObject;

public class Balance implements ValueObject<Double> {

    private final Double value;

    public Balance(Double value) {
        this.value = value;

        if(this.value < 0){
            throw new IllegalArgumentException("Balance can't be less than 0.");
        }
    }

    @Override
    public Double value() {
        return value;
    }
}
