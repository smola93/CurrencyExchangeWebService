package com.exchange.exchangeWS;

import java.io.IOException;
import java.util.Map;

public interface Exchange {
    JsonResult exchangeToPln(Currency currency, double value) throws IOException;
    JsonResult exchangePlnToForeign(Currency currency, double value) throws IOException;
    JsonResult foreignCurrencyExchange(Currency receivedCurrency, Currency exchangeCurrency, double value) throws IOException;
    double calculateCommission (double value);
    boolean currencyValidation(String currency);
    void valueValidation(double value) throws IOException;
}
