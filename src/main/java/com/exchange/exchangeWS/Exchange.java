package com.exchange.exchangeWS;

import java.io.IOException;
import java.util.Map;

public interface Exchange {
    JsonResult exchangeToPln(Currency currency, double value) throws IOException;
    JsonResult exchangePlnToForeign(Currency currency, double value) throws IOException;
    JsonResult foreignCurrencyExchange(Currency firstCurrency, Currency secondCurrency, double value) throws IOException;
    double calculateCommission (double value);
}
