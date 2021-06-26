package com.exchange.exchangews;

import java.io.IOException;

public interface Exchange {
    JsonResult exchangeForeignToPln(Currency currency, double value) throws IOException;

    JsonResult exchangePlnToForeign(Currency currency, double value) throws IOException;

    JsonResult exchangeForeignToForeign(Currency inputCurrency, Currency outputCurrency, double value) throws IOException;

    double calculateCommission(double value);
}
