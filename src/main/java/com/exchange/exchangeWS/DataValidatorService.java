package com.exchange.exchangews;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class DataValidatorService implements DataValidator {
    public boolean currencyValidation(String currency) {
        return Constants.PLN_CODE.equalsIgnoreCase(currency) || Constants.GBP_CODE.equalsIgnoreCase(currency) || Constants.USD_CODE.equalsIgnoreCase(currency) || Constants.EUR_CODE.equalsIgnoreCase(currency);
    }

    public void valueValidation(BigDecimal value) throws IOException {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IOException("Wrong exchange value");
        }
    }}
