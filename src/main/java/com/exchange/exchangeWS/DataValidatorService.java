package com.exchange.exchangews;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DataValidatorService implements DataValidator {
    public boolean currencyValidation(String currency) {
        return Constants.PLN_CODE.equals(currency) || Constants.GBP_CODE.equals(currency) || Constants.USD_CODE.equals(currency) || Constants.EUR_CODE.equals(currency);
    }

    public void valueValidation(double value) throws IOException {
        if (value <= 0) {
            throw new IOException("Wrong exchange value");
        }
    }
}
