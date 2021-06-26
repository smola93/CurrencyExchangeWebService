package com.exchange.exchangews;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public interface DataValidator {
    boolean currencyValidation(String currency);

    void valueValidation(BigDecimal value) throws IOException;
}
