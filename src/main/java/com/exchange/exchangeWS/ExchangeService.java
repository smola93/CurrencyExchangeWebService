package com.exchange.exchangeWS;

import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ExchangeService implements Exchange {

    @Override
    public double calculateCommission (double value){
        value = value - ((value * 2) / 100);
        return value;
    }

    @Override
    public JsonResult exchangeToPln(Currency currency, double value) throws IOException {
        JsonResult json = new JsonResult();
        currency.getRates();
        double rate = currency.rates.get("sell");
        double result = value * rate;
        result = calculateCommission(result);
        json.setReceivedValue(value);
        json.setExchangeValue(result);
        json.setExchangeCurrency("pln");
        json.setReceivedCurrency(currency.code);
        return json;
    }

    @Override
    public JsonResult exchangePlnToForeign(Currency currency, double value) throws IOException {
        JsonResult json = new JsonResult();
        currency.getRates();
        double rate = currency.rates.get("buy");
        double result = value / rate;
        result = calculateCommission(result);
        json.setReceivedValue(value);
        json.setExchangeValue(result);
        json.setReceivedCurrency("pln");
        json.setExchangeCurrency(currency.code);
        return json;
    }

    @Override
    public JsonResult foreignCurrencyExchange(Currency receivedCurrency, Currency exchangeCurrency, double value) throws IOException {
        JsonResult json = new JsonResult();
        receivedCurrency.getRates();
        double receivedCurrencyRate = receivedCurrency.rates.get("sell");
        double receivedCurrencyToPln = value * receivedCurrencyRate;
        receivedCurrencyToPln = calculateCommission(receivedCurrencyToPln);
        exchangeCurrency.getRates();
        double exchangeCurrencyRate = exchangeCurrency.rates.get("buy");
        double result = receivedCurrencyToPln / exchangeCurrencyRate;
        result = calculateCommission(result);
        json.setReceivedValue(value);
        json.setExchangeValue(result);
        json.setReceivedCurrency(receivedCurrency.code);
        json.setExchangeCurrency(exchangeCurrency.code);
        return json;
    }

    @Override
    public boolean currencyValidation(String currency) {
        return currency.equals("pln") || currency.equals("gbp") || currency.equals("usd") || currency.equals("eur");
    }

    @Override
    public void valueValidation(double value) throws IOException {
        if (value <= 0) {
            throw new IOException("Wrong exchange value");
        }
    }
}

