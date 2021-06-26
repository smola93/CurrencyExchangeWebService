package com.exchange.exchangews;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ExchangeService implements Exchange {

    @Override
    public double calculateCommission(double value) {
        value = value - ((value * 2) / 100);
        return value;
    }

    @Override
    public JsonResult exchangeForeignToPln(Currency currency, double value) throws IOException {
        JsonResult json = new JsonResult();
        currency.getRates();
        double rate = currency.rates.get(Constants.SELL_MAP_KEY);
        double result = value * rate;
        result = calculateCommission(result);
        json.setReceivedValue(value);
        json.setExchangeValue(result);
        json.setExchangeCurrency(Constants.PLN_CODE);
        json.setReceivedCurrency(currency.getCode());
        return json;
    }

    @Override
    public JsonResult exchangePlnToForeign(Currency currency, double value) throws IOException {
        JsonResult json = new JsonResult();
        currency.getRates();
        double rate = currency.rates.get(Constants.BUY_MAP_KEY);
        double result = value / rate;
        result = calculateCommission(result);
        json.setReceivedValue(value);
        json.setExchangeValue(result);
        json.setReceivedCurrency(Constants.PLN_CODE);
        json.setExchangeCurrency(currency.getCode());
        return json;
    }

    @Override
    public JsonResult exchangeForeignToForeign(Currency inputCurrency, Currency outputCurrency, double value) throws IOException {
        JsonResult json = new JsonResult();
        inputCurrency.getRates();
        double receivedCurrencyRate = inputCurrency.rates.get(Constants.SELL_MAP_KEY);
        double receivedCurrencyToPln = value * receivedCurrencyRate;
        receivedCurrencyToPln = calculateCommission(receivedCurrencyToPln);
        outputCurrency.getRates();
        double exchangeCurrencyRate = outputCurrency.rates.get(Constants.BUY_MAP_KEY);
        double result = receivedCurrencyToPln / exchangeCurrencyRate;
        result = calculateCommission(result);
        json.setReceivedValue(value);
        json.setExchangeValue(result);
        json.setReceivedCurrency(inputCurrency.getCode());
        json.setExchangeCurrency(outputCurrency.getCode());
        return json;
    }
}

