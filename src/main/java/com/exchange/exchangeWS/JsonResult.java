package com.exchange.exchangeWS;

public class JsonResult {

    String SoldCurrency;
    String BoughtCurrency;
    double receivedValue;
    double exchangeValue;

    public String getSoldCurrency() {
        return SoldCurrency;
    }

    public void setSoldCurrency(String soldCurrency) {
        SoldCurrency = soldCurrency;
    }

    public String getBoughtCurrency() {
        return BoughtCurrency;
    }

    public void setBoughtCurrency(String boughtCurrency) {
        BoughtCurrency = boughtCurrency;
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
