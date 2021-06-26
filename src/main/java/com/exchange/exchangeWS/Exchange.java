package com.exchange.exchangews;

import java.io.IOException;
import java.math.BigDecimal;

public interface Exchange {
    ExchangeResultDto exchangeForeignToPln(Currency currency, BigDecimal value) throws IOException;

    ExchangeResultDto exchangePlnToForeign(Currency currency, BigDecimal value) throws IOException;

    ExchangeResultDto exchangeForeignToForeign(Currency inputCurrency, Currency outputCurrency, BigDecimal value) throws IOException;

    BigDecimal calculateCommission(BigDecimal value);
}
