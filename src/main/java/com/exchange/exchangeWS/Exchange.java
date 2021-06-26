package com.exchange.exchangews;

import java.io.IOException;
import java.math.BigDecimal;

public interface Exchange {
    JsonResult exchangeForeignToPln(Currency currency, BigDecimal value) throws IOException;

    JsonResult exchangePlnToForeign(Currency currency, BigDecimal value) throws IOException;

    JsonResult exchangeForeignToForeign(Currency inputCurrency, Currency outputCurrency, BigDecimal value) throws IOException;

    BigDecimal calculateCommission(BigDecimal value);
}
