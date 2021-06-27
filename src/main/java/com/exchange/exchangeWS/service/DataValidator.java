package com.exchange.exchangews.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public interface DataValidator {
    void validateCurrency(String currency) throws IOException;
    void validateValue(BigDecimal value) throws IOException;
    void validatePln(String currency) throws IOException;
}
