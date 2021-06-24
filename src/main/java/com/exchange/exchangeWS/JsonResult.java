package com.exchange.exchangeWS;

public class JsonResult {

    String receivedCurrency;
    String exchangeCurrency;
    double receivedValue;
    double exchangeValue;

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
