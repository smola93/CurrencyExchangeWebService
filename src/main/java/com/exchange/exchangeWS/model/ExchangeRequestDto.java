package com.exchange.exchangews.model;


import java.io.IOException;
import java.math.BigDecimal;

public class ExchangeRequestDto {
    private String exchangeFrom;
    private String exchangeTo;
    private BigDecimal value;

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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void jsonRequestValidation() throws IOException {
        if (exchangeFrom == null || exchangeTo == null) { //Value is handled in another validation from DataValidator.
            throw new IOException("Invalid request body structure.");
        }
    }
}
