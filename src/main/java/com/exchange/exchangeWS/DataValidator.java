package com.exchange.exchangews;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface DataValidator {
    public boolean currencyValidation(String currency);

    public void valueValidation(double value) throws IOException;
}
