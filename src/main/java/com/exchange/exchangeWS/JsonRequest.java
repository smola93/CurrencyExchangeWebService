package com.exchange.exchangews;

import java.io.IOException;

public class JsonRequest {
    private String exchangeFrom;
    private String exchangeTo;
    private double value;

    public String getExchangeFrom() {
        return exchangeFrom;
    }

    public void setExchangeFrom(String exchangeFrom) {
        this.exchangeFrom = exchangeFrom;
    }

    public String getExchangeTo() {
        return exchangeTo;
    }

    public void setExchangeTo(String exchangeTo) {
        this.exchangeTo = exchangeTo;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void jsonRequestValidation() throws IOException {
        if (exchangeFrom == null || exchangeTo == null) { //Value is handled in another validation from DataValidator.
            throw new IOException("Invalid request body structure.");
        }
    }
}
