package com.exchange.exchangews;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class JsonResult {

    private String receivedCurrency;
    private String exchangeCurrency;
    private BigDecimal receivedValue;
    private BigDecimal exchangeValue;

    public String getReceivedCurrency() {
        return receivedCurrency;
    }

    public void setReceivedCurrency(String receivedCurrency) {
        this.receivedCurrency = receivedCurrency;
    }

    public String getExchangeCurrency() {
        return exchangeCurrency;
    }

    public void setExchangeCurrency(String exchangeCurrency) {
        this.exchangeCurrency = exchangeCurrency;
    }

    public BigDecimal getReceivedValue() {
        return receivedValue;
    }

    public void setReceivedValue(BigDecimal receivedValue) {
        this.receivedValue = receivedValue;
    }

    public BigDecimal getExchangeValue() {
        return exchangeValue.setScale(2, RoundingMode.CEILING);
    }

    public void setExchangeValue(BigDecimal exchangeValue) {
        this.exchangeValue = exchangeValue;
    }
}
