package com.exchange.exchangews;

public class JsonResult {

    private String receivedCurrency;
    private String exchangeCurrency;
    private double receivedValue;
    private double exchangeValue;

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

    public double getReceivedValue() {
        return receivedValue;
    }

    public void setReceivedValue(double receivedValue) {
        this.receivedValue = receivedValue;
    }

    public double getExchangeValue() {
        return exchangeValue;
    }

    public void setExchangeValue(double exchangeValue) {
        this.exchangeValue = exchangeValue;
    }
}
