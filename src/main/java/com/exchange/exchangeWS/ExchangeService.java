package com.exchange.exchangews;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ExchangeService implements Exchange {

    @Override
    public BigDecimal calculateCommission(BigDecimal value) {
        value = value.multiply(BigDecimal.valueOf(0.98));
        return value;
    }

    @Override
    public JsonResult exchangeForeignToPln(Currency currency, BigDecimal value) throws IOException {
        JsonResult json = new JsonResult();
        currency.getRates();
        BigDecimal rate = currency.rates.get(Constants.SELL_MAP_KEY);
        BigDecimal result = value.multiply(rate);
        result = calculateCommission(result);
        json.setReceivedValue(value);
        json.setExchangeValue(result);
        json.setExchangeCurrency(Constants.PLN_CODE);
        json.setReceivedCurrency(currency.getCode());
        return json;
    }

    @Override
    public JsonResult exchangePlnToForeign(Currency currency, BigDecimal value) throws IOException {
        JsonResult json = new JsonResult();
        currency.getRates();
        BigDecimal rate = currency.rates.get(Constants.BUY_MAP_KEY);
        BigDecimal result = value.divide(rate, 2, RoundingMode.HALF_UP);
        result = calculateCommission(result);
        json.setReceivedValue(value);
        json.setExchangeValue(result);
        json.setReceivedCurrency(Constants.PLN_CODE);
        json.setExchangeCurrency(currency.getCode());
        return json;
    }

    @Override
    public JsonResult exchangeForeignToForeign(Currency inputCurrency, Currency outputCurrency, BigDecimal value) throws IOException {
        JsonResult json = new JsonResult();
        inputCurrency.getRates();
        BigDecimal receivedCurrencyRate = inputCurrency.rates.get(Constants.SELL_MAP_KEY);
        BigDecimal receivedCurrencyToPln = value.multiply(receivedCurrencyRate);
        receivedCurrencyToPln = calculateCommission(receivedCurrencyToPln);
        outputCurrency.getRates();
        BigDecimal exchangeCurrencyRate = outputCurrency.rates.get(Constants.BUY_MAP_KEY);
        BigDecimal result = receivedCurrencyToPln.divide(exchangeCurrencyRate, 2, RoundingMode.HALF_UP);
        result = calculateCommission(result);
        json.setReceivedValue(value);
        json.setExchangeValue(result);
        json.setReceivedCurrency(inputCurrency.getCode());
        json.setExchangeCurrency(outputCurrency.getCode());
        return json;
    }
}

