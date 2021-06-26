package com.exchange.exchangews;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class DataValidatorService implements DataValidator {
    public void validateCurrency(String currency) throws IOException {
        if (!Constants.PLN_CODE.equalsIgnoreCase(currency) || !Constants.GBP_CODE.equalsIgnoreCase(currency)
                || !Constants.USD_CODE.equalsIgnoreCase(currency) || !Constants.EUR_CODE.equalsIgnoreCase(currency))
            throw new IOException("Currency " + currency + " is not supported by our service. " +
                    "We support only British Pounds (gbp), United States Dollars (usd), Polish Zloty (pln) and Euro (eur).");
    }

    public void validateValue(BigDecimal value) throws IOException {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IOException("Wrong exchange value");
        }
    }}
